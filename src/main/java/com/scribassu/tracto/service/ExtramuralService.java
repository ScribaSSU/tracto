package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.domain.ExamPeriodMonth;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.dto.web.ExtramuralDto;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ExamPeriodMonthRepository;
import com.scribassu.tracto.repository.ExtramuralEventRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
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

    @Autowired
    public ExtramuralService(DepartmentRepository departmentRepository,
                             StudentGroupRepository studentGroupRepository,
                             ExtramuralEventRepository extramuralEventRepository,
                             ExamPeriodMonthRepository examPeriodMonthRepository) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.extramuralEventRepository = extramuralEventRepository;
        this.examPeriodMonthRepository = examPeriodMonthRepository;
    }

    public ExtramuralDto getExtramuralEventsByGroup(String department, String groupNumber) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.ZO, dep);
        ExtramuralDto extramuralDto = new ExtramuralDto(
                extramuralEventRepository.findByStudentGroup(studentGroup),
                studentGroup);
        log.info("Get extramural event dto: {}", extramuralDto);
        return extramuralDto;
    }

    public ExtramuralDto getExtramuralEventsByGroupAndDay(String department,
                                                          String groupNumber,
                                                          Integer month,
                                                          Integer day) {
        Department dep = departmentRepository.findByURL(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.ZO, dep);
        ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByNumber(month).orElseThrow(IllegalArgumentException::new);
        ExtramuralDto extramuralDto = new ExtramuralDto(
                extramuralEventRepository.findByStudentGroupAndMonthAndDay(studentGroup, examPeriodMonth, day),
                studentGroup
        );
        log.info("Get extramural event dto for day: {}", extramuralDto);
        return extramuralDto;
    }
}
