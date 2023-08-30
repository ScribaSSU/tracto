package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exam_period_month")
@Data
@NoArgsConstructor
public class ExamPeriodMonth {

    @Id
    private Integer number;

    private String rusNominative; // именительный падеж
    private String rusGenitive; // родительный падеж

    private String english;
}
