package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.web.TeacherExamPeriodEventDto;
import com.scribassu.tracto.dto.web.TeacherFullTimeLessonDto;
import com.scribassu.tracto.dto.web.TeacherListDto;
import com.scribassu.tracto.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/word")
    public TeacherListDto findByWord(@RequestBody String word) {
        return teacherService.findByWord(word);
    }

    @GetMapping("/{teacherId}/{day}")
    public TeacherFullTimeLessonDto getLessonsByDay(@PathVariable("teacherId") Long teacherId,
                                                    @PathVariable("day") String dayNumber) {
        return teacherService.getLessonsByDay(teacherId, dayNumber);
    }

    @GetMapping("/{teacherId}/exam")
    public TeacherExamPeriodEventDto getExamPeriodEvents(@PathVariable("teacherId") Long teacherId) {
        return teacherService.getExamPeriodEvents(teacherId);
    }
}
