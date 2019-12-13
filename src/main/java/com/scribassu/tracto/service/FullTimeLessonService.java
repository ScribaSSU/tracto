package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullTimeLessonService {

    private final DayRepository dayRepository;
    private final DepartmentRepository departmentRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final FullTimeLessonRepository fullTimeLessonRepository;

    @Autowired
    public FullTimeLessonService(DayRepository dayRepository,
                                 DepartmentRepository departmentRepository,
                                 LessonTimeRepository lessonTimeRepository,
                                 StudentGroupRepository studentGroupRepository,
                                 FullTimeLessonRepository fullTimeLessonRepository) {
        this.dayRepository = dayRepository;
        this.departmentRepository = departmentRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
    }

    public List<FullTimeLesson> getFullTimeLessonByGroup(String department,
                                                         String educationForm,
                                                         String groupNumber) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.valueOf(educationForm.toUpperCase()), dep);
        return fullTimeLessonRepository.findByStudentGroup(studentGroup);
    }

    public List<FullTimeLesson> getFullTimeLessonByDayAndGroup(int dayNumber,
                                                       String department,
                                                       String educationForm,
                                                       String groupNumber) {
        Day day = dayRepository.findByDayNumber(dayNumber);
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.valueOf(educationForm.toUpperCase()), dep);
        return fullTimeLessonRepository.findByDayAndStudentGroup(day, studentGroup);
    }

    public List<FullTimeLesson> getFullTimeLessonByDayAndLessonTimeAndStudentGroup(int dayNumber,
                                                               int lessonNumber,
                                                               String department,
                                                               String educationForm,
                                                               String groupNumber) {
        Day day = dayRepository.findByDayNumber(dayNumber);
        Department dep = departmentRepository.findByURL(department);
        LessonTime lessonTime = lessonTimeRepository.findByLessonNumber(lessonNumber);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.valueOf(educationForm.toUpperCase()), dep);
        return fullTimeLessonRepository.findByDayAndLessonTimeAndGroup(day, lessonTime, studentGroup);
    }
}
