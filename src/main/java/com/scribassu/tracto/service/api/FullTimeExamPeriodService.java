package com.scribassu.tracto.service.api;

import com.scribassu.tracto.dto.ExamPeriodEventListDto;
import com.scribassu.tracto.entity.schedule.Department;
import com.scribassu.tracto.entity.schedule.EducationForm;
import com.scribassu.tracto.entity.schedule.ExamPeriodMonth;
import com.scribassu.tracto.entity.schedule.StudentGroup;
import com.scribassu.tracto.mapper.HttpMapper;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ExamPeriodEventRepository;
import com.scribassu.tracto.repository.ExamPeriodMonthRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class FullTimeExamPeriodService {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ExamPeriodEventRepository examPeriodEventRepository;
    private final ExamPeriodMonthRepository examPeriodMonthRepository;
    private final HttpMapper httpMapper;

    public ExamPeriodEventListDto getFullTimeExamPeriodByGroup(String department, String groupNumber) {
        Department dep = departmentRepository.findByUrl(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);
        return httpMapper.toExamPeriodEventListDto(examPeriodEventRepository.findByStudentGroup(studentGroup), studentGroup);
    }

    public ExamPeriodEventListDto getFullTimeExamPeriodByGroupAndDay(String department,
                                                                     String groupNumber,
                                                                     Integer month,
                                                                     Integer day) {
        Department dep = departmentRepository.findByUrl(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.DO, dep);
        ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByNumber(month).orElseThrow(IllegalArgumentException::new);
        return httpMapper.toExamPeriodEventListDto(examPeriodEventRepository.findByStudentGroupAndMonthAndDay(studentGroup, examPeriodMonth, day), studentGroup);
    }
}
