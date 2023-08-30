package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lesson_time")
@Data
@NoArgsConstructor
public class LessonTime {

    @Id
    @GeneratedValue
    private Long id;

    private int lessonNumber;
    private int hourStart;
    private int minuteStart;
    private int hourEnd;
    private int minuteEnd;
}
