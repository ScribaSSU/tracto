package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;
    private String shortName;
    private String url;
    private boolean weekShift;
}
