package com.scribassu.tracto.controller;

import com.scribassu.tracto.domain.Teacher;
import com.scribassu.tracto.dto.web.TeacherListDto;
import com.scribassu.tracto.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String[] words = word.split(" "); //in case of more than one part of name
        if(words.length == 1) {
            return new TeacherListDto(teacherRepository.findByAnyPartOfName(word, word, word));
        }
        else {
            StringBuilder stringBuilder = new StringBuilder();
            for(String w : words) {
                stringBuilder.append(w).append(" ");
            }
            String fullName = stringBuilder.toString().trim();
            if(words.length == 2) {
                List<Teacher> teachers = teacherRepository.findByFullNameLike(fullName);
                if(teachers.isEmpty()) {
                    return new TeacherListDto(teacherRepository.findByAnyPartOfName(words[0], words[1], words[0]));
                }
                else {
                    return new TeacherListDto(teachers);
                }
            }
            else {
                List<Teacher> teachers = teacherRepository.findByFullNameLike(fullName);
                if(teachers.isEmpty()) {
                    return new TeacherListDto(teacherRepository.findByAnyPartOfName(words[0], words[1], words[2]));
                }
                else {
                    return new TeacherListDto(teachers);
                }
            }
        }
    }
}
