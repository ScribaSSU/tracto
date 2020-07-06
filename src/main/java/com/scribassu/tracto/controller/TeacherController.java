package com.scribassu.tracto.controller;

import com.scribassu.tracto.dto.web.TeacherListDto;
import com.scribassu.tracto.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/teacher")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public TeacherListDto findByWord(@RequestBody String word) {
        return new TeacherListDto(teacherRepository.findBySurnameOrNameOrPatronymicLike(word, word, word));
    }
}
