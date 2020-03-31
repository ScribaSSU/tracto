package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.dto.web.GroupNumbersDto;
import com.scribassu.tracto.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public GroupNumbersDto getGroupNumbersByDepartmentUrlAndEducationForm(String url,
                                                                          EducationForm educationForm,
                                                                          String course) {
        List<StudentGroup> groups = studentGroupRepository.findByDepartmentUrlAndEducationFormAndCourse(
                url,
                educationForm,
                course
        );
        return new GroupNumbersDto(
                groups.stream().map(StudentGroup::getGroupNumber).collect(Collectors.toList()),
                url,
                educationForm.name()
        );
    }
}
