package com.scribassu.tracto.controller;

import com.scribassu.tracto.config.DocSwagger;
import com.scribassu.tracto.dto.web.DepartmentsListDto;
import com.scribassu.tracto.service.DepartmentsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@DocSwagger
@RestController
@RequestMapping("/v1.0/departments")
public class DepartmentsListController {
    private final DepartmentsListService departmentsListService;

    @Autowired
    public DepartmentsListController(DepartmentsListService departmentsListService) {
        this.departmentsListService = departmentsListService;
    }

    @GetMapping
    public DepartmentsListDto getDepartmentsList() {
        return departmentsListService.getAllDepartments();
    }
}
