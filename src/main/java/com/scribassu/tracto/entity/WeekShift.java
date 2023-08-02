package com.scribassu.tracto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class WeekShift {
    @Id
    @GeneratedValue
    private Long id;
    private int shift;
    private String department;

    public WeekShift(int shift, String department) {
        this.shift = shift;
        this.department = department;
    }
}
