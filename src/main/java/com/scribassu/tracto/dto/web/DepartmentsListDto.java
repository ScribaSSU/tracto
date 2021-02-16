package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentsListDto {
    private List<Department> departmentsList;

    public DepartmentsListDto(List<Department> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public DepartmentsListDto() {
        this.departmentsList = new ArrayList<>();
    }
}
