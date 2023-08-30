package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.DepartmentsListDto;
import com.scribassu.tracto.service.api.DepartmentsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/departments")
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentsService departmentsService;

    @GetMapping
    public DepartmentsListDto getDepartmentsList() {
        return departmentsService.getAllDepartments();
    }
}
