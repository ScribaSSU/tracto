package com.scribassu.tracto.controller;

import com.scribassu.tracto.repository.WeekShiftRepository;
import com.scribassu.tracto.service.scheduled.FullTimeScheduleUpdaterServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/manual")
public class ManualUpdateController {

    @Value("${week-type.access-token}")
    private String accessToken;

    private final FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService;
    private final WeekShiftRepository weekShiftRepository;

    @Autowired
    public ManualUpdateController(FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService,
                                  WeekShiftRepository weekShiftRepository) {
        this.fullTimeScheduleUpdaterService = fullTimeScheduleUpdaterService;
        this.weekShiftRepository = weekShiftRepository;
    }

    @GetMapping("/full")
    public void updateFullTimeScheduleManually() {
        fullTimeScheduleUpdaterService.updateSchedule();
    }

    @GetMapping("/weekShift")
    public ResponseEntity updateWeekShiftForDepartment(@RequestParam("shift") int shift,
                                             @RequestParam("department") String department,
                                             @RequestParam("accessToken") String token) {
        if (!StringUtils.isEmpty(token) && accessToken.equals(token)) {
            if (shift >= 0 && shift <= 1) {
                weekShiftRepository.updateByDepartment(shift, department);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
