package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExamPeriodScheduleParserImpl implements ScheduleParser {

    private static final String PAGE_TITLE_CLASS = "page-title";

    private static final String SESSION_ID = "session";

    private final TeacherRepository teacherRepository;

    private final DepartmentRepository departmentRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final ExamPeriodEventRepository examPeriodEventRepository;

    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    private final ExamPeriodMonthRepository examPeriodMonthRepository;

    @Autowired
    public ExamPeriodScheduleParserImpl(TeacherRepository teacherRepository,
                                        DepartmentRepository departmentRepository,
                                        StudentGroupRepository studentGroupRepository,
                                        ExamPeriodEventRepository examPeriodEventRepository,
                                        ScheduleParserStatusRepository scheduleParserStatusRepository,
                                        ExamPeriodMonthRepository examPeriodMonthRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.examPeriodEventRepository = examPeriodEventRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
        this.examPeriodMonthRepository = examPeriodMonthRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String schedule, String departmentURL) {
        ScheduleParserStatus status = new ScheduleParserStatus();
        Document document = Jsoup.parse(schedule);
        Elements title = document.getElementsByClass(PAGE_TITLE_CLASS);
        Element pageTitle = title.get(0);
        StudentGroup studentGroup = getStudentGroupByPageTitleText(pageTitle.text(), departmentURL);
        if(null == studentGroup) {
            log.error("Fail to find some student group for " + departmentURL);
            status.setStatus("fail no group");
            status.setSchedule("s-unknown" + "-" + departmentURL);
        }
        else {
            try {
                Element sessionTable = document.getElementById(SESSION_ID);
                Elements trs = sessionTable.child(0).children();
                examPeriodEventRepository.deleteByStudentGroup(studentGroup);
                ExamPeriodEvent examPeriodEvent = null;
                boolean firstTd2 = true;
                int lastCorrectDay = -1;
                ExamPeriodMonth lastCorrectExamPeriodMonth = examPeriodMonthRepository.findByNumber(0).orElseThrow(IllegalArgumentException::new);
                String lastCorrectYear = " ";
                int lastCorrectHour = 0;
                int lastCorrectMinute = 0;
                for(Element tr : trs) {
                    Elements tds = tr.children();
                    if(tds.size() == 4) {
                        examPeriodEvent = new ExamPeriodEvent();
                        examPeriodEvent.setStudentGroup(studentGroup);
                        for(int cell = 0; cell < tds.size(); cell++) {
                            switch(cell) {
                                case 0:
                                    String[] date = tds.get(cell).text().split(" ");
                                    try {
                                        if(date[0].startsWith("0")) {
                                            date[0] = date[0].substring(1);
                                        }
                                        int day = Integer.parseInt(date[0]);
                                        ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByRusGenitive(date[1]).get(0);
                                        String year = date[2];
                                        examPeriodEvent.setDay(day);
                                        examPeriodEvent.setMonth(examPeriodMonth);
                                        examPeriodEvent.setYear(year);
                                        lastCorrectDay = day;
                                        lastCorrectExamPeriodMonth = examPeriodMonth;
                                        lastCorrectYear = year;
                                    }
                                    catch(Exception e) {
                                        examPeriodEvent.setDay(lastCorrectDay);
                                        examPeriodEvent.setMonth(lastCorrectExamPeriodMonth);
                                        examPeriodEvent.setYear(lastCorrectYear);
                                    }
                                    break;
                                case 1:
                                    try {
                                        String[] time = tds.get(cell).text().split(":");
                                        int hour = Integer.parseInt(time[0]);
                                        int minute = Integer.parseInt(time[1]);
                                        examPeriodEvent.setHour(hour);
                                        examPeriodEvent.setMinute(minute);
                                        lastCorrectHour = hour;
                                        lastCorrectMinute = minute;
                                    }
                                    catch(Exception e) {
                                        examPeriodEvent.setHour(lastCorrectHour);
                                        examPeriodEvent.setMinute(lastCorrectMinute);
                                    }
                                    break;
                                case 2:
                                    examPeriodEvent.setExamPeriodEventType(getExamPeriodEventType(tds.get(cell).text()));
                                    break;
                                case 3:
                                    examPeriodEvent.setSubjectName(tds.get(cell).text());
                                    break;
                            }
                        }
                    }
                    if(tds.size() == 2) {
                        if(firstTd2) {
                            Teacher teacher = getTeacher(tds.get(1).text());
                            examPeriodEvent.setTeacher(teacher);
                            firstTd2 = false;
                        }
                        else {
                            examPeriodEvent.setPlace(tds.get(1).text());
                            firstTd2 = true;
                            examPeriodEvent = examPeriodEventRepository.save(examPeriodEvent);
                            if(null != examPeriodEvent.getId()) {
                                status.setStatus("ok");
                            }
                            else {
                                status.setStatus("fail to save");
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                status.setStatus("fail " + e);
            }
            status.setSchedule("s-" + studentGroup.getGroupNumber() + "-" + departmentURL);
        }
        status = scheduleParserStatusRepository.save(status);

        return status;
    }

    private StudentGroup getStudentGroupByPageTitleText(String text, String departmentURL) {
        EducationForm educationForm = EducationForm.DO;
        text = text.replace(" группа", "");
        String[] educationForms = {"Дневное", "Заочное", "Вечернее"};
        if(text.startsWith(educationForms[0])) {
            educationForm = EducationForm.DO;
            text = text.replace(educationForms[0], "");
        }
        if(text.startsWith(educationForms[1])) {
            educationForm = EducationForm.ZO;
            text = text.replace(educationForms[1], "");
        }
        if(text.startsWith(educationForms[2])) {
            educationForm = EducationForm.VO;
            text = text.replace(educationForms[2], "");
        }

        text = text.replace("отделение:", "");
        String groupNumber = text.trim();

        return studentGroupRepository.findByNumberAndEducationFormAndDepartment(
                groupNumber,
                educationForm,
                departmentRepository.findByURL(departmentURL)
        );
    }

    private ExamPeriodEventType getExamPeriodEventType(String eventType) {
        if("Зачет:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.MIDTERM;
        }
        if("Дифференцированный зачет:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.MIDTERM_WITH_MARK;
        }
        if("Консультация:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.CONSULTATION;
        }
        if("Экзамен:".equalsIgnoreCase(eventType)) {
            return ExamPeriodEventType.EXAM;
        }
        return ExamPeriodEventType.EXAM;
    }

    private Teacher getTeacher(String text) {
        String[] teacherName = text.split(" ");
        Teacher teacher;
        if(teacherName.length == 3) {
            List<Teacher> teacherList = teacherRepository.findBySurnameAndNameAndPatronymic(
                    teacherName[0],
                    teacherName[1],
                    teacherName[2]);
            if(teacherList.isEmpty()) {
                teacher = new Teacher();
                teacher.setSurname(teacherName[0]);
                teacher.setName(teacherName[1]);
                teacher.setPatronymic(teacherName[2]);
                teacher = teacherRepository.save(teacher);
            }
            else {
                teacher = teacherList.get(0);
            }
        }
        else if(teacherName.length == 1) {
            teacher = findTeacherBySurname(teacherName[0]);
        }
        else {
            teacher = findTeacherBySurname(" ");
        }
        return teacher;
    }

    private Teacher findTeacherBySurname(String surname) {
        Teacher teacher;
        List<Teacher> teacherList = teacherRepository.findBySurname(surname);
        if(teacherList.isEmpty()) {
            teacher = new Teacher();
            teacher.setSurname(surname);
            teacher = teacherRepository.save(teacher);
        }
        else {
            teacher = teacherList.get(0);
        }
        return teacher;
    }
}
