package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.*;
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
import java.util.List;
import java.util.Optional;

@Service
public class FullTimeLessonScheduleParserImpl implements ScheduleParser {

    private final ScheduleDownloader scheduleDownloader;
    private final FullTimeLessonRepository fullTimeLessonRepository;
    private final DepartmentRepository departmentRepository;
    private final DayRepository dayRepository;
    private final TimeRepository timeRepository;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Autowired
    public FullTimeLessonScheduleParserImpl(ScheduleDownloaderImpl scheduleDownloader,
                                            FullTimeLessonRepository fullTimeLessonRepository,
                                            DepartmentRepository departmentRepository,
                                            DayRepository dayRepository,
                                            TimeRepository timeRepository,
                                            ScheduleParserStatusRepository scheduleParserStatusRepository)
                                             {
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
        this.departmentRepository = departmentRepository;
        this.dayRepository = dayRepository;
        this.timeRepository = timeRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String departmentUrl, String scheduleType, String group) {

        if(!scheduleType.equalsIgnoreCase("do")) {
            throw new IllegalArgumentException("Wrong schedule type");
        }

        String scheduleFile = scheduleDownloader.downloadSchedule(departmentUrl, scheduleType, group, false);
        ScheduleParserStatus scheduleParserStatus;

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
            Node row, cell, data;
            NodeList rowList;
            int c = 0;
            int r = -1;

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

                                if(c != 0) {
                                    if(cellContent.contains(WeekType.ODD.getType()) && cellContent.contains(WeekType.EVEN.getType())) {
                                        List<FullTimeLesson> fullTimeLessons = parseTwoLessons(cellContent, group, departmentUrl);
                                    }
                                    else {
                                        FullTimeLesson fullTimeLesson = parseOneLesson(cellContent, departmentUrl, group, r, c);
                                    }
                                }
                            }

                            c++;
                        }
                    }
                    r++;
                    c = 0;
                }
            }

            scheduleParserStatus = new ScheduleParserStatus("upd", scheduleFile);
            scheduleParserStatusRepository.save(scheduleParserStatus);
            return scheduleParserStatus;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
            scheduleParserStatus = new ScheduleParserStatus("fail", scheduleFile);
            scheduleParserStatusRepository.save(scheduleParserStatus);
        }

        return scheduleParserStatus;
    }

    private List<FullTimeLesson> parseTwoLessons(String cellContent, String departmentUrl, String group) {
        return null;
    }

    private FullTimeLesson parseOneLesson(String cellContent, String departmentUrl, String group, int lessonNumber, int dayNumber) {
        Optional<Time> timeOptional = timeRepository.findByLessonNumber(lessonNumber);
        Optional<Day> dayOptional = dayRepository.findByDayNumber(dayNumber);
        FullTimeLesson lesson;

        if(timeOptional.isPresent() && dayOptional.isPresent()) {
            lesson = fullTimeLessonRepository.findByGroupNumberAndLessonTimeAndWeekDay(group, timeOptional.get(), dayOptional.get()).orElse(new FullTimeLesson());
        }
        else {
            throw new IllegalArgumentException("Corrupted full time lesson");
        }

        lesson.setGroupNumber(group);

        if(cellContent.contains(WeekType.ODD.getType())) {
            lesson.setWeekType(WeekType.ODD);
        }
        else if(cellContent.contains(WeekType.EVEN.getType())) {
            lesson.setWeekType(WeekType.EVEN);
        }
        else {
            lesson.setWeekType(WeekType.BOTH);
        }

        if(cellContent.contains(LessonType.LECTURE.getType())) {
            lesson.setLessonType(LessonType.LECTURE);
            cellContent = cellContent.substring(cellContent.indexOf(lesson.getLessonType().getType()) + 5).trim();
        }
        else {
            lesson.setLessonType(LessonType.PRACTICE);
            cellContent = cellContent.substring(cellContent.indexOf(lesson.getLessonType().getType()) + 4).trim();
        }

        if(lesson.getWeekType().equals(WeekType.EVEN) || lesson.getWeekType().equals(WeekType.ODD)) {
            cellContent = cellContent.replace(lesson.getWeekType().getType(), "");
        }
        cellContent = cellContent.trim();

        Optional<Department> departmentOptional = departmentRepository.findByURL(departmentUrl);

        if(departmentOptional.isPresent()) {
            lesson.setDepartment(departmentOptional.get());
        }
        else {
            throw new IllegalArgumentException("Wrong department");
        }

        lesson.setLessonTime(timeOptional.get());
        lesson.setWeekDay(dayOptional.get());
        lesson.setInfo(cellContent);

        fullTimeLessonRepository.save(lesson);
        return lesson;
    }
}
