package com.scribassu.tracto.controller;

import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.scheduled.ExamPeriodScheduleUpdaterServiceImpl;
import com.scribassu.tracto.service.scheduled.ExtramuralScheduleUpdaterServiceImpl;
import com.scribassu.tracto.service.scheduled.FullTimeScheduleUpdaterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/manual")
@RequiredArgsConstructor
public class ManualUpdateController {

    @Value("${week-type.access-token}")
    private String accessToken;

    private final FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService;
    private final ExamPeriodScheduleUpdaterServiceImpl examPeriodScheduleUpdaterService;
    private final ExtramuralScheduleUpdaterServiceImpl extramuralScheduleUpdaterService;
    private final DepartmentRepository departmentRepository;

    @PostMapping("/weekShift")
    public ResponseEntity updateWeekShiftForDepartment(@RequestParam("shift") int shift,
                                             @RequestParam("department") String department,
                                             @RequestParam("accessToken") String token) {
        if (!StringUtils.isEmpty(token) && accessToken.equals(token)) {
            if (shift >= 0 && shift <= 1) {
                departmentRepository.updateByDepartment(shift, department);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/full")
    public void updateFullTimeScheduleManually() {
        fullTimeScheduleUpdaterService.updateSchedule();
    }

    @PostMapping("/exam")
    public void updateExamPeriodScheduleManually() {
        examPeriodScheduleUpdaterService.updateSchedule();
    }

    @PostMapping("/extramural")
    public void updateExtramuralScheduleManually() {
        extramuralScheduleUpdaterService.updateSchedule();
    }
}
