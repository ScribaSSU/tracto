package com.scribassu.tracto.mapper;

import com.scribassu.tracto.dto.EducationForm;
import com.scribassu.tracto.dto.ExamPeriodEventType;
import com.scribassu.tracto.dto.ExtramuralEventType;
import com.scribassu.tracto.dto.GroupType;
import com.scribassu.tracto.dto.LessonType;
import com.scribassu.tracto.dto.WeekDay;
import com.scribassu.tracto.dto.WeekType;
import com.scribassu.tracto.dto.*;
import com.scribassu.tracto.entity.schedule.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HttpMapper {

    // todo переписать по-человечески
    public FullTimeLessonListDto toDto(List<FullTimeLesson> fullTimeLessonList, StudentGroup studentGroup, Day day) {
        StudentGroupDto studentGroupDto = null == studentGroup ? null : toDto(studentGroup);
        DayDto dayDto = null == day ? null : toDto(day);
        return new FullTimeLessonListDto(fullTimeLessonList.stream().map(this::toDto).collect(Collectors.toList()), studentGroupDto, dayDto);
    }

    public FullTimeLessonDto toDto(FullTimeLesson fullTimeLesson) {
        var dto = new FullTimeLessonDto();
        dto.setId(fullTimeLesson.getId());
        dto.setName(fullTimeLesson.getName());
        dto.setStudentGroup(toDto(fullTimeLesson.getStudentGroup()));
        dto.setTeacher(toDto(fullTimeLesson.getTeacher()));
        dto.setPlace(fullTimeLesson.getPlace());
        dto.setDay(toDto(fullTimeLesson.getDay()));
        dto.setLessonTime(toDto(fullTimeLesson.getLessonTime()));
        dto.setLessonType(LessonType.valueOf(fullTimeLesson.getLessonType().name()));
        dto.setSubGroup(fullTimeLesson.getSubGroup());
        dto.setWeekType(WeekType.valueOf(fullTimeLesson.getWeekType().name()));
        dto.setDepartment(toDto(fullTimeLesson.getDepartment()));
        dto.setUpdatedTimestamp(fullTimeLesson.getUpdatedTimestamp());
        dto.setBeginTimestamp(fullTimeLesson.getBeginTimestamp());
        dto.setEndTimestamp(fullTimeLesson.getEndTimestamp());
        return dto;
    }

    public DayDto toDto(Day day) {
        var dto = new DayDto();
        dto.setId(day.getId());
        dto.setDayNumber(day.getDayNumber());
        dto.setWeekDay(WeekDay.valueOf(day.getWeekDay().name()));
        return dto;
    }

    public StudentGroupDto toDto(StudentGroup studentGroup) {
        var dto = new StudentGroupDto();
        dto.setId(studentGroup.getId());
        dto.setDepartment(toDto(studentGroup.getDepartment()));
        dto.setEducationForm(EducationForm.valueOf(studentGroup.getEducationForm().name()));
        dto.setGroupNumber(studentGroup.getGroupNumber());
        dto.setGroupNumberRus(studentGroup.getGroupNumberRus());
        dto.setGroupType(GroupType.valueOf(studentGroup.getGroupType().name()));
        return dto;
    }

    public DepartmentDto toDto(Department department) {
        var dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setFullName(department.getFullName());
        dto.setShortName(department.getShortName());
        dto.setUrl(department.getUrl());
        dto.setWeekShift(department.isWeekShift());
        return dto;
    }

    public TeacherDto toDto(Teacher teacher) {
        var dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setSurname(teacher.getSurname());
        dto.setName(teacher.getName());
        dto.setPatronymic(teacher.getPatronymic());
        return dto;
    }

    public LessonTimeDto toDto(LessonTime lessonTime) {
        var dto = new LessonTimeDto();
        dto.setId(lessonTime.getId());
        dto.setLessonNumber(lessonTime.getLessonNumber());
        dto.setHourStart(lessonTime.getHourStart());
        dto.setMinuteStart(lessonTime.getMinuteStart());
        dto.setHourEnd(lessonTime.getHourEnd());
        dto.setMinuteEnd(lessonTime.getMinuteEnd());
        return dto;
    }

    public ExamPeriodEventListDto toExamPeriodEventListDto(List<ExamPeriodEvent> examPeriodEvents, StudentGroup studentGroup) {
        return new ExamPeriodEventListDto(examPeriodEvents.stream().map(this::toDto).collect(Collectors.toList()), toDto(studentGroup));
    }

    public ExamPeriodEventDto toDto(ExamPeriodEvent examPeriodEvent) {
        var dto = new ExamPeriodEventDto();
        dto.setId(examPeriodEvent.getId());
        dto.setPlace(examPeriodEvent.getPlace());
        dto.setTeacher(toDto(examPeriodEvent.getTeacher()));
        dto.setStudentGroup(toDto(examPeriodEvent.getStudentGroup()));
        dto.setExamPeriodEventType(ExamPeriodEventType.valueOf(examPeriodEvent.getExamPeriodEventType().name()));
        dto.setSubjectName(examPeriodEvent.getSubjectName());
        dto.setYear(examPeriodEvent.getYear());
        dto.setMonth(toDto(examPeriodEvent.getMonth()));
        dto.setDay(examPeriodEvent.getDay());
        dto.setHour(examPeriodEvent.getHour());
        dto.setMinute(examPeriodEvent.getMinute());
        return dto;
    }

    public ExamPeriodMonthDto toDto(ExamPeriodMonth examPeriodMonth) {
        return new ExamPeriodMonthDto(examPeriodMonth.getNumber(), examPeriodMonth.getRusNominative(), examPeriodMonth.getRusGenitive(), examPeriodMonth.getEnglish());
    }

    public ExtramuralEventListDto toExtramuralEventListDto(List<ExtramuralEvent> extramuralEvents, StudentGroup studentGroup) {
        return new ExtramuralEventListDto(extramuralEvents.stream().map(this::toDto).collect(Collectors.toList()), toDto(studentGroup));
    }

    public ExtramuralEventDto toDto(ExtramuralEvent extramuralEvent) {
        var dto = new ExtramuralEventDto();
        dto.setId(extramuralEvent.getId());
        dto.setDepartment(toDto(extramuralEvent.getDepartment()));
        dto.setPlace(extramuralEvent.getPlace());
        dto.setName(extramuralEvent.getName());
        dto.setDay(extramuralEvent.getDay());
        dto.setMonth(toDto(extramuralEvent.getMonth()));
        dto.setYear(extramuralEvent.getYear());
        dto.setEndHour(extramuralEvent.getEndHour());
        dto.setEndMinute(extramuralEvent.getEndMinute());
        dto.setEventType(ExtramuralEventType.valueOf(extramuralEvent.getEventType().name()));
        dto.setStartHour(extramuralEvent.getStartHour());
        dto.setStartMinute(extramuralEvent.getStartMinute());
        dto.setTeacher(extramuralEvent.getTeacher());
        dto.setStudentGroup(toDto(extramuralEvent.getStudentGroup()));
        return dto;
    }

    public TeacherListDto toTeacherListDto(List<Teacher> teachers) {
        return new TeacherListDto(teachers.stream().map(this::toDto).collect(Collectors.toList()));
    }

    public TeacherFullTimeLessonListDto toTeacherFullTimeLessonListDto(List<FullTimeLesson> lessons, Teacher teacher, Day day) {
        return new TeacherFullTimeLessonListDto(lessons.stream().map(this::toDto).collect(Collectors.toList()), toDto(teacher), toDto(day));
    }

    public TeacherExamPeriodEventListDto toTeacherExamPeriodEventDto(List<ExamPeriodEvent> events, Teacher teacher) {
        return new TeacherExamPeriodEventListDto(events.stream().map(this::toDto).collect(Collectors.toList()), toDto(teacher));
    }

    public TeacherExtramuralEventListDto toTeacherExtramuralEventDto(List<ExtramuralEvent> events, Teacher teacher) {
        return new TeacherExtramuralEventListDto(events.stream().map(this::toDto).collect(Collectors.toList()), toDto(teacher));
    }

    public DepartmentsListDto toDepartmentsListDto(List<Department> departmentsList) {
        return new DepartmentsListDto(departmentsList.stream().map(this::toDto).collect(Collectors.toList()));
    }
}
