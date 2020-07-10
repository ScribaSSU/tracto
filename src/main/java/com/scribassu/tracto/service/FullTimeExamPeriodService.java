package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.dto.web.ExamPeriodEventDto;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ExamPeriodEventRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FullTimeExamPeriodService {

    private final DepartmentRepository departmentRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final ExamPeriodEventRepository examPeriodEventRepository;

    @Autowired
    public FullTimeExamPeriodService(DepartmentRepository departmentRepository,
                                     StudentGroupRepository studentGroupRepository,
                                     ExamPeriodEventRepository examPeriodEventRepository) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.examPeriodEventRepository = examPeriodEventRepository;
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
}
