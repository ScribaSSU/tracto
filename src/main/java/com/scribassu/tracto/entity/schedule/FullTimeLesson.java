package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "full_time_lesson")
@Data
@NoArgsConstructor
public class FullTimeLesson {

    @Id
    @GeneratedValue
    private Long id;

    // Название предмета
    private String name;
    // Место проведения предмета
    private String place;

    @ManyToOne
    private Department department;

    @ManyToOne
    private StudentGroup studentGroup;

    // Подгруппа
    private String subGroup;

    @ManyToOne
    private Day day;

    @ManyToOne
    private LessonTime lessonTime;

    @ManyToOne
    Teacher teacher;

    // По каким неделям идет пара - по числителям, знаменателям или каждую неделю
    @Enumerated(EnumType.STRING)
    private WeekType weekType;

    // Тип пары - лекция, практика или лабораторная
    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    private Long updatedTimestamp;

    private Long beginTimestamp;

    private Long endTimestamp;
}
