package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeacherListDto {

    private List<Teacher> teachers;

    public TeacherListDto(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public TeacherListDto() {
        this.teachers = new ArrayList<>();
    }
}
