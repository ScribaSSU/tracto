package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.FullTimeLesson;
import com.scribassu.tracto.domain.StudentGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FullTimeLessonDto {

    private List<FullTimeLesson> lessons;
    private StudentGroup studentGroup;
    private Day day;

    public FullTimeLessonDto(List<FullTimeLesson> lessons,
                             StudentGroup studentGroup) {
        this.lessons = lessons;
        this.studentGroup = studentGroup;
        this.day = new Day();
    }

    public FullTimeLessonDto(List<FullTimeLesson> lessons,
                             StudentGroup studentGroup,
                             Day day) {
        this.lessons = lessons;
        this.studentGroup = studentGroup;
        this.day = day;
    }
}
