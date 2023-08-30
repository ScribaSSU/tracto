package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.ExtramuralEventListDto;
import com.scribassu.tracto.service.api.ExtramuralService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/schedule/extramural")
@AllArgsConstructor
public class ExtramuralController {
    private final ExtramuralService extramuralService;

    @GetMapping("/{department}/{groupNumber}")
    public ExtramuralEventListDto getExtramuralEventsByGroup(@PathVariable("department") String department,
                                                             @PathVariable("groupNumber") String groupNumber) {
        return extramuralService.getExtramuralEventsByGroup(department, groupNumber);
    }

    @GetMapping("/{department}/{groupNumber}/{month}/{day}")
    public ExtramuralEventListDto getExtramuralEventsByDay(@PathVariable("department") String department,
                                                       @PathVariable("groupNumber") String groupNumber,
                                                       @PathVariable("month") Integer month,
                                                       @PathVariable("day") Integer day) {
        return extramuralService.getExtramuralEventsByGroupAndDay(department, groupNumber, month, day);
    }
}
