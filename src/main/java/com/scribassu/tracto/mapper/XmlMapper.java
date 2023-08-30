package com.scribassu.tracto.mapper;

import com.scribassu.tracto.dto.xml.GroupXml;
import com.scribassu.tracto.dto.xml.LessonXml;
import com.scribassu.tracto.dto.xml.TeacherXml;
import com.scribassu.tracto.entity.schedule.*;
import org.springframework.stereotype.Component;

@Component
public class XmlMapper {

    public LessonType toLessonType(String type) {
        switch (type) {
            case "lecture":
                return LessonType.LECTURE;
            case "practice":
                return LessonType.PRACTICE;
            case "laboratory":
                return LessonType.LABORATORY;
            default:
                return null;
        }
    }

    public WeekType toWeekType(String weekType) {
        return WeekType.valueOf(weekType.toUpperCase());
    }

    public GroupType toGroupType(int groupType) {
        switch (groupType) {
            case -1:
                return GroupType.COLLEGE;
            case 0:
                return GroupType.SPECIALTY;
            case 1:
                return GroupType.BACHELOR;
            case 2:
                return GroupType.MASTER;
            case 3:
                return GroupType.GRADUATE_SCHOOL;
            default:
                return null;
        }
    }

    public EducationForm toEducationForm(int educationForm) {
        switch (educationForm) {
            case 0:
                return EducationForm.DO;
            case 1:
                return EducationForm.ZO;
            case 2:
                return EducationForm.VO;
            default:
                return null;
        }
    }

    public Teacher toTeacher(TeacherXml teacherXml) {
        var teacher = new Teacher();
        teacher.setSurname(teacherXml.lastname);
        teacher.setName(teacherXml.name);
        teacher.setPatronymic(teacherXml.patronymic);
        return teacher;
    }

    public StudentGroup toStudentGroup(GroupXml groupXml, Department department) {
        var studentGroup = new StudentGroup();
        studentGroup.setDepartment(department);
        studentGroup.setGroupNumber(groupXml.number.trim());
        studentGroup.setGroupNumberRus(groupXml.numberRus.trim());
        studentGroup.setGroupType(toGroupType(groupXml.groupType));
        studentGroup.setEducationForm(toEducationForm(groupXml.eduForm));
        return studentGroup;
    }

    public FullTimeLesson toFullTimeLesson(LessonXml lessonXml, Day day, LessonTime lessonTime, Teacher teacher, StudentGroup studentGroup) {
        FullTimeLesson lesson = new FullTimeLesson();
        lesson.setStudentGroup(studentGroup);
        lesson.setDepartment(studentGroup.getDepartment());
        lesson.setName(lessonXml.name);
        lesson.setPlace(lessonXml.place);
        lesson.setSubGroup(lessonXml.subgroup);
        lesson.setDay(day);
        lesson.setLessonTime(lessonTime);
        lesson.setWeekType(toWeekType(lessonXml.weekType));
        lesson.setTeacher(teacher);
        lesson.setLessonType(toLessonType(lessonXml.type));
        if (null != lessonXml.updated && !lessonXml.updated.isBlank()) {
            lesson.setUpdatedTimestamp(Long.parseLong(lessonXml.updated));
        }
        if (null != lessonXml.dateBegin && !lessonXml.dateBegin.isBlank()) {
            lesson.setBeginTimestamp(Long.parseLong(lessonXml.dateBegin));
        }
        if (null != lessonXml.dateEnd && !lessonXml.dateEnd.isBlank()) {
            lesson.setEndTimestamp(Long.parseLong(lessonXml.dateEnd));
        }
        return lesson;
    }
}
