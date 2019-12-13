package com.scribassu.tracto.controller.v1v0;

import com.scribassu.tracto.domain.FullTimeLesson;
import com.scribassu.tracto.service.FullTimeLessonService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1.0/schedule/full")
public class FullTimeLessonController {

    private final FullTimeLessonService fullTimeLessonService;

    public FullTimeLessonController(FullTimeLessonService fullTimeLessonService) {
        this.fullTimeLessonService = fullTimeLessonService;
    }

    @GetMapping("/{department}/{educationForm}/{groupNumber}")
    public List<FullTimeLesson> getFullTimeLessonEntityList(@PathVariable("department") String department,
                                                            @PathVariable("educationForm") String educationForm,
                                                            @PathVariable("groupNumber") String groupNumber) {
        return fullTimeLessonService.getFullTimeLessonByGroup(department, educationForm, groupNumber);
    }

    @GetMapping("/{department}/{educationForm}/{groupNumber}/{dayNumber}")
    public List<FullTimeLesson> getFullTimeLessonByDay(@PathVariable("department") String department,
                                                       @PathVariable("educationForm") String educationForm,
                                                       @PathVariable("groupNumber") String groupNumber,
                                                       @PathVariable("dayNumber") int dayNumber) {
        return fullTimeLessonService.getFullTimeLessonByDayAndGroup(dayNumber, department, educationForm, groupNumber);
    }

    @GetMapping("/{department}/{educationForm}/{groupNumber}/{dayNumber}/{lessonNumber}")
    public List<FullTimeLesson> getFullTimeLessonByDayAndGroupNumber(@PathVariable("department") String department,
                                                                     @PathVariable("educationForm") String educationForm,
                                                                     @PathVariable("groupNumber") String groupNumber,
                                                                     @PathVariable("dayNumber") int dayNumber,
                                                                     @PathVariable("lessonNumber") int lessonNumber) {
        return fullTimeLessonService.getFullTimeLessonByDayAndLessonTimeAndStudentGroup(
                dayNumber,
                lessonNumber,
                department,
                educationForm,
                groupNumber
        );
    }
}
