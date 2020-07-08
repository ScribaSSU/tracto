package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeacherListDto {

    private List<Teacher> teachers;

    public TeacherListDto(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
