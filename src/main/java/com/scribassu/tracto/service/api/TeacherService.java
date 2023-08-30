package com.scribassu.tracto.service.api;

import com.scribassu.tracto.dto.TeacherExamPeriodEventListDto;
import com.scribassu.tracto.dto.TeacherExtramuralEventListDto;
import com.scribassu.tracto.dto.TeacherFullTimeLessonListDto;
import com.scribassu.tracto.dto.TeacherListDto;
import com.scribassu.tracto.entity.schedule.Day;
import com.scribassu.tracto.entity.schedule.ExamPeriodEvent;
import com.scribassu.tracto.entity.schedule.FullTimeLesson;
import com.scribassu.tracto.entity.schedule.Teacher;
import com.scribassu.tracto.mapper.HttpMapper;
import com.scribassu.tracto.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TeacherService {

    private final DayRepository dayRepository;

    private final TeacherRepository teacherRepository;

    private final FullTimeLessonRepository fullTimeLessonRepository;

    private final ExamPeriodEventRepository examPeriodEventRepository;
    private final ExtramuralEventRepository extramuralEventRepository;
    private final HttpMapper httpMapper;

    public TeacherListDto findByWord(String word) {
        String[] words = word.split(" "); //in case of more than one part of name
        if (words.length == 1) {
            List<Teacher> teachers = teacherRepository.findByAnyPartOfName(word, word, word);
            if (teachers.isEmpty()) {
                return new TeacherListDto();
            } else {
                return httpMapper.toTeacherListDto(teachers);
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String w : words) {
                stringBuilder.append(w).append(" ");
            }
            String fullName = stringBuilder.toString().trim();
            List<Teacher> teachers = teacherRepository.findByFullNameLike(fullName);
            if (teachers.isEmpty()) {
                if (words.length == 2) {
                    teachers = teacherRepository.findByAnyPartOfName(words[0], words[1], words[0]);
                } else {
                    teachers = teacherRepository.findByAnyPartOfName(words[0], words[1], words[2]);
                }
                if (teachers.isEmpty()) {
                    return new TeacherListDto();
                } else {
                    return httpMapper.toTeacherListDto(teachers);
                }
            } else {
                return httpMapper.toTeacherListDto(teachers);
            }
        }
    }

    public TeacherFullTimeLessonListDto getLessonsByDay(Long teacherId, String dayNumber) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(IllegalArgumentException::new);
        Day day = dayRepository.findByDayNumber(Integer.parseInt(dayNumber));
        List<FullTimeLesson> lessons = fullTimeLessonRepository.findByTeacher(teacher, day);
        return httpMapper.toTeacherFullTimeLessonListDto(lessons, teacher, day);
    }

    public TeacherExamPeriodEventListDto getExamPeriodEvents(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(IllegalArgumentException::new);
        List<ExamPeriodEvent> events = examPeriodEventRepository.findByTeacher(teacher);
        return httpMapper.toTeacherExamPeriodEventDto(events, teacher);
    }

    public TeacherExtramuralEventListDto getExtramuralEventsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.getById(teacherId);
        String teacherName = teacher.getSurname() + " " + teacher.getName().charAt(0) + ". " + teacher.getPatronymic().charAt(0) + ".";
        return httpMapper.toTeacherExtramuralEventDto(
                extramuralEventRepository.findByTeacher(teacherName),
                teacher
        );
    }

    public TeacherListDto getAllTeachers() {
        return httpMapper.toTeacherListDto(teacherRepository.findAll());
    }
}
