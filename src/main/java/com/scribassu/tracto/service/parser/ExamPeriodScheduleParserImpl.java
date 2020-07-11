package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamPeriodScheduleParserImpl implements ScheduleParser {

    private static final String PAGE_TITLE_CLASS = "page-title";

    private static final String SESSION_ID = "session";

    private final TeacherRepository teacherRepository;

    private final DepartmentRepository departmentRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final ExamPeriodEventRepository examPeriodEventRepository;

    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Autowired
    public ExamPeriodScheduleParserImpl(TeacherRepository teacherRepository,
                                        DepartmentRepository departmentRepository,
                                        StudentGroupRepository studentGroupRepository,
                                        ExamPeriodEventRepository examPeriodEventRepository,
                                        ScheduleParserStatusRepository scheduleParserStatusRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.examPeriodEventRepository = examPeriodEventRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String schedule, String departmentURL) {
        Document document = Jsoup.parse(schedule);
        Elements title = document.getElementsByClass(PAGE_TITLE_CLASS);
        Element pageTitle = title.get(0);
        StudentGroup studentGroup = getStudentGroupByPageTitleText(pageTitle.text(), departmentURL);
        if(null == studentGroup) {
            System.out.println("AAAAAAAAAA");
        }
        ScheduleParserStatus status = new ScheduleParserStatus();
        try {
            Element sessionTable = document.getElementById(SESSION_ID);
            Elements trs = sessionTable.child(0).children();
            examPeriodEventRepository.deleteByStudentGroup(studentGroup);
            ExamPeriodEvent examPeriodEvent = null;
            boolean firstTd2 = true;
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
                                    examPeriodEvent.setDay(Integer.parseInt(date[0]));
                                    examPeriodEvent.setMonth(date[1]);
                                    examPeriodEvent.setYear(date[2]);
                                }
                                catch(Exception e) {
                                    examPeriodEvent.setDay(-1);
                                    examPeriodEvent.setMonth(" ");
                                    examPeriodEvent.setYear(" ");
                                }
                                break;
                            case 1:
                                try {
                                    String[] time = tds.get(cell).text().split(":");
                                    examPeriodEvent.setHour(Integer.parseInt(time[0]));
                                    examPeriodEvent.setMinute(Integer.parseInt(time[1]));
                                }
                                catch(Exception e) {
                                    examPeriodEvent.setHour(-1);
                                    examPeriodEvent.setMinute(0);
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
                            status.setStatus("fail");
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            status.setStatus("fail");
        }
        status.setSchedule("s-" + studentGroup.getGroupNumber() + "-" + departmentURL);
        status = scheduleParserStatusRepository.save(status);

        return status;
    }

    private StudentGroup getStudentGroupByPageTitleText(String text, String departmentURL) {
        EducationForm educationForm = null;
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
