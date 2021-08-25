package com.scribassu.tracto.controller;

import com.scribassu.tracto.config.DocSwagger;
import com.scribassu.tracto.dto.web.ExtramuralEventDto;
import com.scribassu.tracto.dto.web.TeacherExtramuralEventDto;
import com.scribassu.tracto.service.ExtramuralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@DocSwagger
@RestController
@RequestMapping("/v1.0/schedule/extramural")
public class ExtramuralController {
    private final ExtramuralService extramuralService;

    public ExtramuralController(ExtramuralService extramuralService) {
        this.extramuralService = extramuralService;
    }

    @GetMapping("/{department}/{groupNumber}")
    public ExtramuralEventDto getExtramuralEventsByGroup(@PathVariable("department") String department,
                                                         @PathVariable("groupNumber") String groupNumber) {
        return extramuralService.getExtramuralEventsByGroup(department, groupNumber);
    }

    @GetMapping("/{department}/{groupNumber}/{month}/{day}")
    public ExtramuralEventDto getExtramuralEventsByDay(@PathVariable("department") String department,
                                                       @PathVariable("groupNumber") String groupNumber,
                                                       @PathVariable("month") Integer month,
                                                       @PathVariable("day") Integer day) {
        return extramuralService.getExtramuralEventsByGroupAndDay(department, groupNumber, month, day);
    }

    @GetMapping("/teacher/{teacherId}")
    public TeacherExtramuralEventDto ExtramuralEventsByTeacher(@PathVariable("teacherId") Long teacherId) {
        return extramuralService.getExtramuralEventsByTeacher(teacherId);
    }
}
