package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.ExamPeriodEventListDto;
import com.scribassu.tracto.service.api.FullTimeExamPeriodService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/exam/full")
@AllArgsConstructor
public class FullTimeExamPeriodController {

    private final FullTimeExamPeriodService fullTimeExamPeriodService;

    @GetMapping("/{department}/{groupNumber}")
    public ExamPeriodEventListDto getFullTimeExamPeriod(@PathVariable("department") String department,
                                                        @PathVariable("groupNumber") String groupNumber) {
        return fullTimeExamPeriodService.getFullTimeExamPeriodByGroup(department, groupNumber);
    }

    @GetMapping("/{department}/{groupNumber}/{month}/{day}")
    public ExamPeriodEventListDto getFullTimeExamPeriodByDay(@PathVariable("department") String department,
                                                         @PathVariable("groupNumber") String groupNumber,
                                                         @PathVariable("month") Integer month,
                                                         @PathVariable("day") Integer day) {
        return fullTimeExamPeriodService.getFullTimeExamPeriodByGroupAndDay(department, groupNumber, month, day);
    }
}
