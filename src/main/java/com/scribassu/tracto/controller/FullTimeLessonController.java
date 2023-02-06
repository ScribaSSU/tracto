package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.web.FullTimeLessonDto;
import com.scribassu.tracto.service.FullTimeLessonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/schedule/full")
public class FullTimeLessonController {

    private final FullTimeLessonService fullTimeLessonService;

    public FullTimeLessonController(FullTimeLessonService fullTimeLessonService) {
        this.fullTimeLessonService = fullTimeLessonService;
    }

    @GetMapping("/{department}/{groupNumber}")
    public FullTimeLessonDto getFullTimeLesson(@PathVariable("department") String department,
                                               @PathVariable("groupNumber") String groupNumber) {
        return fullTimeLessonService.getFullTimeLessonByGroup(department, groupNumber);
    }

    @GetMapping("/{department}/{groupNumber}/{dayNumber}")
    public FullTimeLessonDto getFullTimeLessonByDay(@PathVariable("department") String department,
                                                    @PathVariable("groupNumber") String groupNumber,
                                                    @PathVariable("dayNumber") int dayNumber) {
        return fullTimeLessonService.getFullTimeLessonByDayAndGroup(dayNumber, department, groupNumber);
    }

    @GetMapping("/{department}/{groupNumber}/{dayNumber}/{lessonNumber}")
    public FullTimeLessonDto getFullTimeLessonByDayAndLessonTimeAndStudentGroup(@PathVariable("department") String department,
                                                                                @PathVariable("groupNumber") String groupNumber,
                                                                                @PathVariable("dayNumber") int dayNumber,
                                                                                @PathVariable("lessonNumber") int lessonNumber) {
        return fullTimeLessonService.getFullTimeLessonByDayAndLessonTimeAndStudentGroup(
                dayNumber,
                lessonNumber,
                department,
                groupNumber
        );
    }
}
