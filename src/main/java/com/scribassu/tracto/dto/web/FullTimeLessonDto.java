package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.FullTimeLesson;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.entity.WeekShift;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FullTimeLessonDto {

    private List<FullTimeLesson> lessons;

    private StudentGroup studentGroup;

    private Day day;

    private WeekShift weekShift;

    public FullTimeLessonDto(List<FullTimeLesson> lessons,
                             StudentGroup studentGroup,
                             WeekShift weekShift) {
        this.lessons = lessons;
        this.studentGroup = studentGroup;
        this.day = new Day();
        this.weekShift = weekShift;
    }

    public FullTimeLessonDto(List<FullTimeLesson> lessons,
                             StudentGroup studentGroup,
                             Day day,
                             WeekShift weekShift) {
        this.lessons = lessons;
        this.studentGroup = studentGroup;
        this.day = day;
        this.weekShift = weekShift;
    }
}
