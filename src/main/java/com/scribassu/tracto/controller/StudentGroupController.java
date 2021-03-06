package com.scribassu.tracto.controller;

import com.scribassu.tracto.config.DocSwagger;
import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.dto.web.GroupNumbersDto;
import com.scribassu.tracto.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@DocSwagger
@RestController
@RequestMapping("/v1.0/group")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @GetMapping("/number/{departmentUrl}/{educationForm}/{course}")
    public GroupNumbersDto getGroupNumbersByDepartmentUrlAndEducationFormAndCourse(
            @PathVariable("departmentUrl") String departmentUrl,
            @PathVariable("educationForm") String educationForm,
            @PathVariable("course") String course) {
        return studentGroupService.getGroupNumbersByDepartmentUrlAndEducationFormAndCourse(
                departmentUrl, EducationForm.valueOf(educationForm.toUpperCase()), course
        );
    }

    @GetMapping("/number/{departmentUrl}/{educationForm}/other")
    public GroupNumbersDto getGroupNumbersByDepartmentUrlAndEducationForm(
            @PathVariable("departmentUrl") String departmentUrl,
            @PathVariable("educationForm") String educationForm) {
        return studentGroupService.getOtherGroupNumbersByDepartmentUrlAndEducationForm(
                departmentUrl, EducationForm.valueOf(educationForm.toUpperCase())
        );
    }
}
