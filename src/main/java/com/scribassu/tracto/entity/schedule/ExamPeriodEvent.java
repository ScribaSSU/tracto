package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exam_period_event")
@Data
@NoArgsConstructor
public class ExamPeriodEvent {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExamPeriodEventType examPeriodEventType;

    private int day;

    @ManyToOne
    private ExamPeriodMonth month;

    private String year;
    private int hour;
    private int minute;
    private String subjectName;

    @ManyToOne
    private Teacher teacher;

    @OneToOne
    private StudentGroup studentGroup;
    private String place;
}