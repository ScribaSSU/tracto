package com.scribassu.tracto.controller.v1v0;

import com.scribassu.tracto.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1.0/group")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @GetMapping("/number/{departmentUrl}/{educationForm}")
    public List<String> getGroupNumbersByDepartmentUrlAndEducationForm(
            @PathVariable("departmentUrl") String departmentUrl,
            @PathVariable("educationForm") String educationForm) {
        return studentGroupService.getGroupNumbersByDepartmentUrlAndEducationForm(
                departmentUrl, educationForm
        );
    }
}
