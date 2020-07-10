package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.ExamPeriodEvent;
import com.scribassu.tracto.domain.FullTimeLesson;
import com.scribassu.tracto.domain.Teacher;
import com.scribassu.tracto.dto.web.TeacherExamPeriodEventDto;
import com.scribassu.tracto.dto.web.TeacherFullTimeLessonDto;
import com.scribassu.tracto.dto.web.TeacherListDto;
import com.scribassu.tracto.repository.DayRepository;
import com.scribassu.tracto.repository.ExamPeriodEventRepository;
import com.scribassu.tracto.repository.FullTimeLessonRepository;
import com.scribassu.tracto.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final DayRepository dayRepository;
    private final TeacherRepository teacherRepository;
    private final FullTimeLessonRepository fullTimeLessonRepository;
    private final ExamPeriodEventRepository examPeriodEventRepository;

    public TeacherService(DayRepository dayRepository,
                          TeacherRepository teacherRepository,
                          FullTimeLessonRepository fullTimeLessonRepository,
                          ExamPeriodEventRepository examPeriodEventRepository) {
        this.dayRepository = dayRepository;
        this.teacherRepository = teacherRepository;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
        this.examPeriodEventRepository = examPeriodEventRepository;
    }


    public TeacherListDto findByWord(String word) {
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

    public TeacherFullTimeLessonDto getLessonsByDay(Long teacherId, String dayNumber) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(IllegalArgumentException::new);
        Day day = dayRepository.findByDayNumber(Integer.parseInt(dayNumber));
        List<FullTimeLesson> lessons = fullTimeLessonRepository.findByTeacher(teacher, day);
        return new TeacherFullTimeLessonDto(lessons, teacher, day);
    }

    public TeacherExamPeriodEventDto getExamPeriodEvents(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(IllegalArgumentException::new);
        List<ExamPeriodEvent> events = examPeriodEventRepository.findByTeacher(teacher);
        return new TeacherExamPeriodEventDto(events, teacher);
    }
}
