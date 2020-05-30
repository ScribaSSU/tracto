package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.ExamPeriodEvent;
import com.scribassu.tracto.domain.ExamPeriodEventType;
import com.scribassu.tracto.domain.Teacher;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.TeacherRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionParserImpl implements ScheduleParser {

    private final TeacherRepository teacherRepository;

    @Autowired
    public SessionParserImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String schedule, String department) {
        Document document = Jsoup.parse(schedule);
        Element sessionTable = document.getElementById("session");
        Elements trs = sessionTable.child(0).children();
        ExamPeriodEvent examPeriodEvent = null;
        List<ExamPeriodEvent> test = new ArrayList<>();
        boolean firstTd2 = true;
        for(Element tr : trs) {
            Elements tds = tr.children();
            if(tds.size() == 4) {
                examPeriodEvent = new ExamPeriodEvent();
                System.out.println("SESSION ITEM BEGINS");
                for(int cell = 0; cell < tds.size(); cell++) {
                    switch(cell) {
                        case 0:
                            String[] date = tds.get(cell).text().split(" ");
                            examPeriodEvent.setDay(Integer.parseInt(date[0]));
                            //examPeriodEvent.setMonth(date[1]);
                            break;
                        case 1:
                            String[] time = tds.get(cell).text().split(":");
                            examPeriodEvent.setHour(Integer.parseInt(time[0]));
                            examPeriodEvent.setMinute(Integer.parseInt(time[1]));
                            break;
                        case 2:
                            examPeriodEvent.setExamPeriodEventType(getExamPeriodEventType(tds.get(cell).text()));
                            break;
                        case 3:
                            examPeriodEvent.setSubjectName(tds.get(cell).text());
                            break;
                    }
                    System.out.println(cell + " td4:" + tds.get(cell).text());
                }
            }
            if(tds.size() == 2) {
                if(firstTd2) {
                    String[] teacherName = tds.get(1).text().split(" ");
                    Teacher teacher;
                    if(teacherName.length == 3) {
                        List<Teacher> teacherList = teacherRepository.findBySurnameAndNameAndPatronymic(
                                teacherName[0],
                                teacherName[1],
                                teacherName[2]);
                        try {
                            teacher = teacherList.get(0);
                        } catch(Exception e) {
                            teacher = new Teacher();
                            teacher.setSurname(teacherName[0]);
                            teacher.setName(teacherName[1]);
                            teacher.setPatronymic(teacherName[2]);
                            teacher = teacherRepository.save(teacher);
                        }
                    }
                    else {
                        teacher = new Teacher();
                        teacher.setSurname(teacherName[0]);
                        teacher = teacherRepository.save(teacher);
                    }
                    examPeriodEvent.setTeacher(teacher);
                    firstTd2 = false;
                }
                else {
                    examPeriodEvent.setPlace(tds.get(1).text());
                    test.add(examPeriodEvent);
                    firstTd2 = true;
                }
            }
        }

        System.out.println(test.size());
        return null;
    }

    private ExamPeriodEventType getExamPeriodEventType(String eventType) {
        if("Зачет:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.MIDTERM;
        }
        if("Консультация:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.CONSULTATION;
        }
        if("Экзамен:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.EXAM;
        }
        return ExamPeriodEventType.EXAM;
    }
}
