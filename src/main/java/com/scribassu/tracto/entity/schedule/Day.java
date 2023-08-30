package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "day")
@Data
@NoArgsConstructor
public class Day {
    @Id
    @GeneratedValue
    private Long id;

    // Номер дня
    private int dayNumber;

    // Краткое строковое представление дня
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;
}
