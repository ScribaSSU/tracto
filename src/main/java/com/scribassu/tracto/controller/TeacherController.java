package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.TeacherExamPeriodEventListDto;
import com.scribassu.tracto.dto.TeacherExtramuralEventListDto;
import com.scribassu.tracto.dto.TeacherFullTimeLessonListDto;
import com.scribassu.tracto.dto.TeacherListDto;
import com.scribassu.tracto.service.api.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/teacher")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/word")
    public TeacherListDto findByWord(@RequestBody String word) {
        return teacherService.findByWord(word);
    }

    @GetMapping("/{teacherId}/full/{day}")
    public TeacherFullTimeLessonListDto getLessonsByDay(@PathVariable("teacherId") Long teacherId,
                                                        @PathVariable("day") String dayNumber) {
        return teacherService.getLessonsByDay(teacherId, dayNumber);
    }

    @GetMapping("/{teacherId}/exam")
    public TeacherExamPeriodEventListDto getExamPeriodEvents(@PathVariable("teacherId") Long teacherId) {
        return teacherService.getExamPeriodEvents(teacherId);
    }

    @GetMapping("/{teacherId}/extramural")
    public TeacherExtramuralEventListDto getExtramuralEventsByTeacher(@PathVariable("teacherId") Long teacherId) {
        return teacherService.getExtramuralEventsByTeacher(teacherId);
    }

    @GetMapping("/all")
    public TeacherListDto getAllTeachers() {
        return teacherService.getAllTeachers();
    }
}
