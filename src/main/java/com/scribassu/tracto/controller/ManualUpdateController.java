package com.scribassu.tracto.controller;

import com.scribassu.tracto.service.scheduled.ExamPeriodScheduleUpdaterServiceImpl;
import com.scribassu.tracto.service.scheduled.ExtramuralScheduleUpdaterServiceImpl;
import com.scribassu.tracto.service.scheduled.FullTimeScheduleUpdaterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/manual")
public class ManualUpdateController {

    private final FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService;
    private final ExamPeriodScheduleUpdaterServiceImpl examPeriodScheduleUpdaterService;
    private final ExtramuralScheduleUpdaterServiceImpl extramuralScheduleUpdaterService;

    @Autowired
    public ManualUpdateController(FullTimeScheduleUpdaterServiceImpl fullTimeScheduleUpdaterService,
                                  ExamPeriodScheduleUpdaterServiceImpl examPeriodScheduleUpdaterService,
                                  ExtramuralScheduleUpdaterServiceImpl extramuralScheduleUpdaterService) {
        this.fullTimeScheduleUpdaterService = fullTimeScheduleUpdaterService;
        this.examPeriodScheduleUpdaterService = examPeriodScheduleUpdaterService;
        this.extramuralScheduleUpdaterService = extramuralScheduleUpdaterService;
    }

    @GetMapping("/full")
    public void updateFullTimeScheduleManually() {
        fullTimeScheduleUpdaterService.updateSchedule();
    }

    @GetMapping("/exam")
    public void updateExamPeriodScheduleManually() {
        examPeriodScheduleUpdaterService.updateSchedule();
    }

    @GetMapping("/extramural")
    public void updateExtramuralScheduleManually() {
        extramuralScheduleUpdaterService.updateSchedule();
    }
}
