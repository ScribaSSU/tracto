package com.scribassu.tracto.entity.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue  
    private Long id;

    private String surname;
    private String name;
    private String patronymic;
}
