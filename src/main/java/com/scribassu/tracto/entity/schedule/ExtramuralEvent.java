package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "extramural_event")
@Data
@NoArgsConstructor
public class ExtramuralEvent {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ExamPeriodMonth month;
    private int day;
    private String year;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    @ManyToOne
    private Department department;

    @OneToOne
    private StudentGroup studentGroup;

    @Enumerated(EnumType.STRING)
    private ExtramuralEventType eventType;

    private String name;
    private String place;
    private String teacher;

    public ExtramuralEvent clone () {
        ExtramuralEvent e = new ExtramuralEvent();
        e.month = this.month;
        e.day = this.day;
        e.year = this.year;
        e.startHour = this.startHour;
        e.startMinute = this.startMinute;
        e.endHour = this.endHour;
        e.endMinute = this.endMinute;
        e.department = this.department;
        e.studentGroup = this.studentGroup;
        e.eventType = this.eventType;
        e.name = this.name;
        e.place = this.place;
        e.teacher = this.teacher;

        return e;
    }
}
