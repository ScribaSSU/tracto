package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.GroupNumbersDto;
import com.scribassu.tracto.entity.schedule.EducationForm;
import com.scribassu.tracto.service.api.StudentGroupService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/group")
@AllArgsConstructor
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

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
