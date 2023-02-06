package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.web.ExamPeriodEventDto;
import com.scribassu.tracto.service.FullTimeExamPeriodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/exam/full")
public class FullTimeExamPeriodController {

    private final FullTimeExamPeriodService fullTimeExamPeriodService;

    public FullTimeExamPeriodController(FullTimeExamPeriodService fullTimeExamPeriodService) {
        this.fullTimeExamPeriodService = fullTimeExamPeriodService;
    }

    @GetMapping("/{department}/{groupNumber}")
    public ExamPeriodEventDto getFullTimeExamPeriod(@PathVariable("department") String department,
                                                    @PathVariable("groupNumber") String groupNumber) {
        return fullTimeExamPeriodService.getFullTimeExamPeriodByGroup(department, groupNumber);
    }

    @GetMapping("/{department}/{groupNumber}/{month}/{day}")
    public ExamPeriodEventDto getFullTimeExamPeriodByDay(@PathVariable("department") String department,
                                                         @PathVariable("groupNumber") String groupNumber,
                                                         @PathVariable("month") Integer month,
                                                         @PathVariable("day") Integer day) {
        return fullTimeExamPeriodService.getFullTimeExamPeriodByGroupAndDay(department, groupNumber, month, day);
    }
}
