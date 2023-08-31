package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.TeacherExamPeriodEventListDto;
import com.scribassu.tracto.dto.TeacherExtramuralEventListDto;
import com.scribassu.tracto.dto.TeacherFullTimeLessonListDto;
import com.scribassu.tracto.dto.TeacherListDto;
import com.scribassu.tracto.service.api.TeacherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/teacher")
@Slf4j
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/word")
    public TeacherListDto findByWord(@RequestBody String word) {
        log.info("POST /v1.0/teacher/word");
        return teacherService.findByWord(word);
    }

    @GetMapping("/all")
    public TeacherListDto getAllTeachers() {
        log.info("GET /v1.0/teacher/all");
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{teacherId}/full/{day}")
    public TeacherFullTimeLessonListDto getLessonsByDay(@PathVariable("teacherId") Long teacherId,
                                                        @PathVariable("day") String dayNumber) {
        log.info("GET /v1.0/teacher/{}/full/{}", teacherId, dayNumber);
        return teacherService.getLessonsByDay(teacherId, dayNumber);
    }

    @GetMapping("/{teacherId}/exam")
    public TeacherExamPeriodEventListDto getExamPeriodEvents(@PathVariable("teacherId") Long teacherId) {
        log.info("GET /v1.0/teacher/{}/exam", teacherId);
        return teacherService.getExamPeriodEvents(teacherId);
    }

    @GetMapping("/{teacherId}/extramural")
    public TeacherExtramuralEventListDto getExtramuralEventsByTeacher(@PathVariable("teacherId") Long teacherId) {
        log.info("GET /v1.0/teacher/{}/extramural", teacherId);
        return teacherService.getExtramuralEventsByTeacher(teacherId);
    }
}
