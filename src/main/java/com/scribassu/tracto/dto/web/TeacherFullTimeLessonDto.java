package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.FullTimeLesson;
import com.scribassu.tracto.domain.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeacherFullTimeLessonDto {

    private List<FullTimeLesson> lessons;
    private Teacher teacher;
    private Day day;

    public TeacherFullTimeLessonDto(List<FullTimeLesson> lessons,
                                    Teacher teacher) {
        this.lessons = lessons;
        this.teacher = teacher;
        this.day = new Day();
    }

    public TeacherFullTimeLessonDto(List<FullTimeLesson> lessons,
                                    Teacher teacher,
                                    Day day) {
        this.lessons = lessons;
        this.teacher = teacher;
        this.day = day;
    }
}
