package com.scribassu.tracto.service.api;

import com.scribassu.tracto.dto.ExtramuralEventListDto;
import com.scribassu.tracto.entity.schedule.Department;
import com.scribassu.tracto.entity.schedule.EducationForm;
import com.scribassu.tracto.entity.schedule.ExamPeriodMonth;
import com.scribassu.tracto.entity.schedule.StudentGroup;
import com.scribassu.tracto.mapper.HttpMapper;
import com.scribassu.tracto.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ExtramuralService {
    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ExtramuralEventRepository extramuralEventRepository;
    private final ExamPeriodMonthRepository examPeriodMonthRepository;
    private final TeacherRepository teacherRepository;
    private final HttpMapper httpMapper;

    public ExtramuralEventListDto getExtramuralEventsByGroup(String departmentUrl, String groupNumber) {
        Department department = departmentRepository.findByUrl(departmentUrl);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.ZO, department);
        return httpMapper.toExtramuralEventListDto(extramuralEventRepository.findByStudentGroup(studentGroup), studentGroup);
    }

    public ExtramuralEventListDto getExtramuralEventsByGroupAndDay(String department,
                                                               String groupNumber,
                                                               Integer month,
                                                               Integer day) {
        Department dep = departmentRepository.findByUrl(department);
        StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(groupNumber, EducationForm.ZO, dep);
        ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByNumber(month).orElseThrow(IllegalArgumentException::new);
        return httpMapper.toExtramuralEventListDto(extramuralEventRepository.findByStudentGroupAndMonthAndDay(studentGroup, examPeriodMonth, day), studentGroup);
    }
}
