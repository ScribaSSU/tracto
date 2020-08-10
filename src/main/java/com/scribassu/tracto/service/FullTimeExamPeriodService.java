package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.dto.web.ExamPeriodEventDto;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ExamPeriodEventRepository;
import com.scribassu.tracto.repository.ExamPeriodMonthRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FullTimeExamPeriodService {

    private final DepartmentRepository departmentRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final ExamPeriodEventRepository examPeriodEventRepository;

    private final ExamPeriodMonthRepository examPeriodMonthRepository;

    @Autowired
    public FullTimeExamPeriodService(DepartmentRepository departmentRepository,
                                     StudentGroupRepository studentGroupRepository,
                                     ExamPeriodEventRepository examPeriodEventRepository,
                                     ExamPeriodMonthRepository examPeriodMonthRepository) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.examPeriodEventRepository = examPeriodEventRepository;
        this.examPeriodMonthRepository = examPeriodMonthRepository;
    }

    public ExamPeriodEventDto getFullTimeExamPeriodByGroup(String department, String groupNumber) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);
        ExamPeriodEventDto examPeriodEventDto = new ExamPeriodEventDto(
                examPeriodEventRepository.findByStudentGroup(studentGroup),
                studentGroup
        );
        log.info("Get exam period event dto: {}", examPeriodEventDto);
        return examPeriodEventDto;
    }

    public ExamPeriodEventDto getFullTimeExamPeriodByGroupAndDay(String department,
                                                                 String groupNumber,
                                                                 Integer month,
                                                                 Integer day) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);
        ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByNumber(month).orElseThrow(IllegalArgumentException::new);
        ExamPeriodEventDto examPeriodEventDto = new ExamPeriodEventDto(
                examPeriodEventRepository.findByStudentGroupAndMonthAndDay(studentGroup, examPeriodMonth, day),
                studentGroup
        );
        log.info("Get exam period event dto for day: {}", examPeriodEventDto);
        return examPeriodEventDto;
    }
}
