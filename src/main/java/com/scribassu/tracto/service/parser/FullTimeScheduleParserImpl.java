package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.dto.xml.*;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

@Service
public class FullTimeScheduleParserImpl implements ScheduleParser {

    private final FullTimeLessonRepository fullTimeLessonRepository;
    private final DayRepository dayRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Autowired
    public FullTimeScheduleParserImpl(FullTimeLessonRepository fullTimeLessonRepository,
                                      DayRepository dayRepository,
                                      LessonTimeRepository lessonTimeRepository,
                                      StudentGroupRepository studentGroupRepository,
                                      DepartmentRepository departmentRepository,
                                      TeacherRepository teacherRepository,
                                      ScheduleParserStatusRepository scheduleParserStatusRepository) {
        this.dayRepository = dayRepository;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String schedule, String departmentURL) {
        ScheduleParserStatus scheduleParserStatus;
        try {
            StringReader stringReader = new StringReader(schedule);
            JAXBContext jaxbContext = JAXBContext.newInstance(ScheduleXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ScheduleXml scheduleXml = (ScheduleXml) unmarshaller.unmarshal(stringReader);

            fullTimeLessonRepository.deleteByDepartmentURL(departmentURL);
            parseGroups(scheduleXml.groups, departmentURL);

            scheduleParserStatus = new ScheduleParserStatus("ok", departmentURL);
            scheduleParserStatusRepository.save(scheduleParserStatus);
        }
        catch(Exception e) {
            e.printStackTrace();
            scheduleParserStatus = new ScheduleParserStatus("fail", departmentURL);
            scheduleParserStatusRepository.save(scheduleParserStatus);
        }

        return scheduleParserStatus;
    }

    private void parseGroups(List<GroupXml> groups, String department) {
        Department dep = departmentRepository.findByURL(department);

        if(dep == null) {
            throw new IllegalArgumentException("Wrong department!");
        }

        for(GroupXml group : groups) {
            EducationForm educationForm = convertEducationForm(group.eduForm);
            StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(group.numberRus, educationForm, dep);
            if(studentGroup == null) {
                studentGroup = new StudentGroup();
            }
            studentGroup.setDepartment(dep);
            studentGroup.setGroupNumber(group.number);
            studentGroup.setGroupNumberRus(group.numberRus);
            studentGroup.setGroupType(convertGroupType(group.groupType));
            studentGroup.setEducationForm(educationForm);
            studentGroupRepository.save(studentGroup);

            parseDays(group.days, studentGroup);
        }
    }

    private void parseDays(List<DayXml> days, StudentGroup studentGroup) {
        for(DayXml d : days) {
            Day day = dayRepository.findByDayNumber(d.id);

            if(day == null) {
                throw new IllegalArgumentException("Wrong day!");
            }

            LessonsXml lessonsXml = d.lessons;
            if(!CollectionUtils.isEmpty(lessonsXml.lessons)) {
                parseLessons(lessonsXml.lessons, day, studentGroup);
            }
        }
    }

    private void parseLessons(List<LessonXml> lessons, Day day, StudentGroup studentGroup) {
        for(LessonXml les : lessons) {
            LessonTime lessonTime = lessonTimeRepository.findByLessonNumber(les.number);
            FullTimeLesson lesson = new FullTimeLesson();
            lesson.setStudentGroup(studentGroup);
            lesson.setDepartment(studentGroup.getDepartment());
            lesson.setName(les.name);
            lesson.setPlace(les.place);
            lesson.setSubGroup(les.subgroup);
            lesson.setDay(day);
            lesson.setLessonTime(lessonTime);
            lesson.setWeekType(convertWeekType(les.weekType));
            lesson.setTeacher(parseTeacher(les.teacher));
            lesson.setLessonType(convertLessonType(les.type));
            fullTimeLessonRepository.save(lesson);
        }
    }

    private Teacher parseTeacher(TeacherXml teacher) {
        List<Teacher> tList = teacherRepository.findBySurnameAndNameAndPatronymic(teacher.lastname, teacher.name, teacher.patronymic);

        if(tList.size() > 1) {
            //TODO: log warning
        }

        Teacher t;

        if(CollectionUtils.isEmpty(tList)) {
            t = new Teacher();
        }
        else {
            t = tList.get(0);
        }

        t.setSurname(teacher.lastname);
        t.setName(teacher.name);
        t.setPatronymic(teacher.patronymic);
        teacherRepository.save(t);
        return t;
    }

    private LessonType convertLessonType(String type) {
        switch (type) {
            case "lecture": return LessonType.LECTURE;
            case "practice": return LessonType.PRACTICE;
            case "laboratory": return LessonType.LABORATORY;
            default: return null;
        }
    }

    private WeekType convertWeekType(String weekType) {
        return WeekType.valueOf(weekType.toUpperCase());
    }

    private GroupType convertGroupType(int groupType) {
        switch(groupType) {
            case 0: return GroupType.SPECIALTY;
            case 1: return GroupType.BACHELOR;
            case 2: return GroupType.MASTER;
            case 3: return GroupType.GRADUATE_SCHOOL;
            default: return null;
        }
    }

    private EducationForm convertEducationForm(int educationForm) {
        switch(educationForm) {
            case 0: return EducationForm.DO;
            case 1: return EducationForm.ZO;
            case 2: return EducationForm.VO;
            default: return null;
        }
    }
}