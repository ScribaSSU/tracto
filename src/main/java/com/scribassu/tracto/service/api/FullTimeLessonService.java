package com.scribassu.tracto.service.api;

import com.scribassu.tracto.dto.FullTimeLessonListDto;
import com.scribassu.tracto.entity.schedule.*;
import com.scribassu.tracto.mapper.HttpMapper;
import com.scribassu.tracto.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class FullTimeLessonService {
    private final DayRepository dayRepository;
    private final DepartmentRepository departmentRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final FullTimeLessonRepository fullTimeLessonRepository;

    private final HttpMapper httpMapper;

    public FullTimeLessonListDto getFullTimeLessonByDepartment(String departmentUrl) {
        Department department = departmentRepository.findByUrl(departmentUrl);
        return httpMapper.toDto(fullTimeLessonRepository.findByDepartment(department), null, null);
    }

    public FullTimeLessonListDto getFullTimeLessonByGroup(String departmentUrl,
                                                          String groupNumber) {
        Department department = departmentRepository.findByUrl(departmentUrl);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, department);
        return httpMapper.toDto(fullTimeLessonRepository.findByStudentGroup(studentGroup), studentGroup, null);
    }

    public FullTimeLessonListDto getFullTimeLessonByDayAndGroup(int dayNumber,
                                                                String departmentUrl,
                                                                String groupNumber) {
        Day day = dayRepository.findByDayNumber(dayNumber);
        Department department = departmentRepository.findByUrl(departmentUrl);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, department);
        return httpMapper.toDto(
                fullTimeLessonRepository.findByDayAndStudentGroup(day, studentGroup),
                studentGroup,
                day
        );
    }

    public FullTimeLessonListDto getFullTimeLessonByDayAndLessonTimeAndStudentGroup(int dayNumber,
                                                                                    int lessonNumber,
                                                                                    String departmentUrl,
                                                                                    String groupNumber) {
        Day day = dayRepository.findByDayNumber(dayNumber);
        Department department = departmentRepository.findByUrl(departmentUrl);
        LessonTime lessonTime = lessonTimeRepository.findByLessonNumber(lessonNumber);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, department);

        return httpMapper.toDto(
                fullTimeLessonRepository.findByDayAndLessonTimeAndGroup(day, lessonTime, studentGroup),
                studentGroup,
                day
        );
    }
}
