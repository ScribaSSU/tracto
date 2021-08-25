package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.dto.web.ExtramuralEventDto;
import com.scribassu.tracto.dto.web.TeacherExtramuralEventDto;
import com.scribassu.tracto.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExtramuralService {
    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ExtramuralEventRepository extramuralEventRepository;
    private final ExamPeriodMonthRepository examPeriodMonthRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public ExtramuralService(DepartmentRepository departmentRepository,
                             StudentGroupRepository studentGroupRepository,
                             ExtramuralEventRepository extramuralEventRepository,
                             ExamPeriodMonthRepository examPeriodMonthRepository,
                             TeacherRepository teacherRepository) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.extramuralEventRepository = extramuralEventRepository;
        this.examPeriodMonthRepository = examPeriodMonthRepository;
        this.teacherRepository = teacherRepository;
    }

    public ExtramuralEventDto getExtramuralEventsByGroup(String department, String groupNumber) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.ZO, dep);
        ExtramuralEventDto extramuralEventDto = new ExtramuralEventDto(
                extramuralEventRepository.findByStudentGroup(studentGroup),
                studentGroup);
        log.info("Get extramural event dto: {}", extramuralEventDto);
        return extramuralEventDto;
    }

    public ExtramuralEventDto getExtramuralEventsByGroupAndDay(String department,
                                                               String groupNumber,
                                                               Integer month,
                                                               Integer day) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.ZO, dep);
        ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByNumber(month).orElseThrow(IllegalArgumentException::new);
        ExtramuralEventDto extramuralEventDto = new ExtramuralEventDto(
                extramuralEventRepository.findByStudentGroupAndMonthAndDay(studentGroup, examPeriodMonth, day),
                studentGroup
        );
        log.info("Get extramural event dto for day: {}", extramuralEventDto);
        return extramuralEventDto;
    }

    public TeacherExtramuralEventDto getExtramuralEventsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.getById(teacherId);
        String teacherName = teacher.getSurname() + " " + teacher.getName().charAt(0) + ". " + teacher.getPatronymic().charAt(0) + ".";
        TeacherExtramuralEventDto teacherExtramuralEventDto = new TeacherExtramuralEventDto(
                extramuralEventRepository.findByTeacher(teacherName),
                teacher
        );
        log.info("Get extramural event dto for teacher: {}", teacherExtramuralEventDto);
        return teacherExtramuralEventDto;
    }
}
