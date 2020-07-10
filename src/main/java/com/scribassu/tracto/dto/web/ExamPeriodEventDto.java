package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.ExamPeriodEvent;
import com.scribassu.tracto.domain.StudentGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExamPeriodEventDto {

    private List<ExamPeriodEvent> examPeriodEvents;

    private StudentGroup studentGroup;

    public ExamPeriodEventDto(List<ExamPeriodEvent> examPeriodEvents,
                              StudentGroup studentGroup) {
        this.examPeriodEvents = examPeriodEvents;
        this.studentGroup = studentGroup;
    }
}
