package com.scribassu.tracto.controller;

import com.scribassu.tracto.service.scheduled.FullTimeScheduleUpdaterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/manual")
public class ManualUpdateController {

    private final FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService;

    @Autowired
    public ManualUpdateController(FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService) {
        this.fullTimeScheduleUpdaterService = fullTimeScheduleUpdaterService;
    }

    @GetMapping("/full")
    public void updateFullTimeScheduleManually() {
        fullTimeScheduleUpdaterService.updateSchedule();
    }
}
