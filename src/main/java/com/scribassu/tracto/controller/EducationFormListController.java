package com.scribassu.tracto.controller;

import com.scribassu.tracto.config.DocSwagger;
import com.scribassu.tracto.dto.web.EducationFormListDto;
import com.scribassu.tracto.service.EducationFormListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@DocSwagger
@RestController
@RequestMapping("/v1.0/educationforms")
public class EducationFormListController {
    private final EducationFormListService educationFormListService;

    @Autowired
    public EducationFormListController(EducationFormListService educationFormListService) {
        this.educationFormListService = educationFormListService;
    }

    @GetMapping
    public EducationFormListDto getEducationList(){
        return educationFormListService.getEducationFormList();
    }
}
