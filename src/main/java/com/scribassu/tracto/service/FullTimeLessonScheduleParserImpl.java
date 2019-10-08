package com.scribassu.tracto.service;

import com.scribassu.tracto.entity.ScheduleParserStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class FullTimeLessonScheduleParserImpl implements ScheduleParser {

    private final ScheduleDownloader scheduleDownloader;

    @Autowired
    public FullTimeLessonScheduleParserImpl(ScheduleDownloaderImpl scheduleDownloader) {
        this.scheduleDownloader = scheduleDownloader;
    }

    @Override
    public ScheduleParserStatusEntity parseSchedule(String department, String scheduleType, String group) {
        String scheduleFile = scheduleDownloader.downloadSchedule(department, scheduleType, group, false);
        initLessonDays();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(scheduleFile);
            Node root = document.getDocumentElement();
            NodeList rootChildren = root.getChildNodes();

            int i = 0;
            Node worksheet = root;

            while(!worksheet.getNodeName().equals("Worksheet")) {
                worksheet = rootChildren.item(i);
                i++;
            }

            NodeList worksheetList = worksheet.getChildNodes();

            i = 0;
            Node table = root;

            while(!table.getNodeName().equals("Table")) {
                table = worksheetList.item(i);
                i++;
            }

            NodeList tableList = table.getChildNodes();

            Node row;
            NodeList rowList;
            Node cell;
            Node data;

            int c = 0;
            int r = -1;

            List<LessonTime> lessonTimeList = new ArrayList<>();

            for(int k = 0; k < tableList.getLength(); k++) {
                row = tableList.item(k);

                if(row.getNodeName().equals("Row")) {
                    rowList = row.getChildNodes();

                    for(int u = 0; u < rowList.getLength(); u++) {
                        cell = rowList.item(u);

                        if(cell.getNodeName().equals("Cell")) {
                            data = cell.getChildNodes().item(0);
                            String cellContent = data.getTextContent();

                            if(cellContent.isEmpty()) {
                                c++;
                                continue;
                            }

                            //row = 0 contains days of week
                            if(r != -1 && r != 0) {

                                if(c == 0) {
                                    String[] time = cellContent.split("-");
                                    lessonTimeList.add(new LessonTime(r, time[0].trim(), time[1].trim()));
                                }
                                else {
                                    Lesson lesson = new Lesson(group);

                                    if(cellContent.contains(LessonType.NOMINATOR)) {
                                        lesson.setWeek(LessonType.NOMINATOR);
                                    }
                                    else if(cellContent.contains(LessonType.DENOMINATOR)) {
                                        lesson.setWeek(LessonType.DENOMINATOR);
                                    }
                                    else {
                                        lesson.setWeek(LessonType.BOTH);
                                    }

                                    if(cellContent.contains(LessonType.LECTURE)) {
                                        lesson.setType(LessonType.LECTURE);
                                        //cellContent = cellContent.substring(cellContent.indexOf(lesson.getType()) + 5).trim();
                                    }
                                    else {
                                        lesson.setType(LessonType.PRACTICE);
                                        //cellContent = cellContent.substring(cellContent.indexOf(lesson.getType()) + 4).trim();
                                    }

                                    cellContent = cellContent.replace(lesson.getType(), "");
                                    cellContent = cellContent.replace(lesson.getWeek(), "");
                                    cellContent = cellContent.trim();

                                    lesson.setContent(cellContent);
                                    lesson.setLessonTimeId(r);
                                    lesson.setDayId(c);
                                    lesson.setId(r * 100000 + c * 10000 + lesson.getWeek().length() * 1000 + Long.parseLong(group));
                                    lessonRepository.saveOrUpdate(lesson);
                                }
                            }

                            c++;
                        }
                    }
                    r++;
                    c = 0;
                }
            }

            utilRepository.initLessonTime(lessonTimeList);
            ScheduleParserStatusEntity scheduleParserStatusEntity = new ScheduleParserStatusEntity("upd", scheduleFile);
            //TODO: save to repo
            return scheduleParserStatusEntity;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
            ScheduleParserStatusEntity scheduleParserStatusEntity = new ScheduleParserStatusEntity("fail", scheduleFile);
            //TODO: save to repo
        }
    }

    private void initLessonDays() {
        ArrayList<LessonDay> lessonDays = new ArrayList<>();
        lessonDays.add(new LessonDay(1, "Понедельник"));
        lessonDays.add(new LessonDay(2, "Вторник"));
        lessonDays.add(new LessonDay(3, "Среда"));
        lessonDays.add(new LessonDay(4, "Четверг"));
        lessonDays.add(new LessonDay(5, "Пятница"));
        lessonDays.add(new LessonDay(6, "Суббота"));
        lessonDays.add(new LessonDay(7, "Воскресенье"));
        utilRepository.initLessonDays(lessonDays);
    }
}
