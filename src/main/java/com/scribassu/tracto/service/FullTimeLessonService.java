package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.dto.web.FullTimeLessonDto;
import com.scribassu.tracto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FullTimeLessonService {

    private final DayRepository dayRepository;

    private final DepartmentRepository departmentRepository;

    private final LessonTimeRepository lessonTimeRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final FullTimeLessonRepository fullTimeLessonRepository;

    private final WeekShiftRepository weekShiftRepository;

    @Autowired
    public FullTimeLessonService(DayRepository dayRepository,
                                 DepartmentRepository departmentRepository,
                                 LessonTimeRepository lessonTimeRepository,
                                 StudentGroupRepository studentGroupRepository,
                                 FullTimeLessonRepository fullTimeLessonRepository,
                                 WeekShiftRepository weekShiftRepository) {
        this.dayRepository = dayRepository;
        this.departmentRepository = departmentRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
        this.weekShiftRepository = weekShiftRepository;
    }

    public FullTimeLessonDto getFullTimeLessonByDepartment(String department) {
        Department dep = departmentRepository.findByURL(department);
        return new FullTimeLessonDto(fullTimeLessonRepository.findByDepartment(dep), null, null);
    }

    public FullTimeLessonDto getFullTimeLessonByGroup(String department,
                                                      String groupNumber) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);
        return new FullTimeLessonDto(
                fullTimeLessonRepository.findByStudentGroup(studentGroup),
                studentGroup,
                weekShiftRepository.findByDepartment(dep.getURL()).get()
        );
    }

    public FullTimeLessonDto getFullTimeLessonByDayAndGroup(int dayNumber,
                                                            String department,
                                                            String groupNumber) {
        Day day = dayRepository.findByDayNumber(dayNumber);
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);
        return new FullTimeLessonDto(
                fullTimeLessonRepository.findByDayAndStudentGroup(day, studentGroup),
                studentGroup,
                day,
                weekShiftRepository.findByDepartment(dep.getURL()).get()
        );
    }

    public FullTimeLessonDto getFullTimeLessonByDayAndLessonTimeAndStudentGroup(int dayNumber,
                                                                                int lessonNumber,
                                                                                String department,
                                                                                String groupNumber) {
        Day day = dayRepository.findByDayNumber(dayNumber);
        Department dep = departmentRepository.findByURL(department);
        LessonTime lessonTime = lessonTimeRepository.findByLessonNumber(lessonNumber);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);

        return new FullTimeLessonDto(
                fullTimeLessonRepository.findByDayAndLessonTimeAndGroup(day, lessonTime, studentGroup),
                studentGroup,
                day,
                weekShiftRepository.findByDepartment(dep.getURL()).get()
        );
    }
}
