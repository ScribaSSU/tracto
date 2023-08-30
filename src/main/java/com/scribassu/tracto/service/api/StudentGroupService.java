package com.scribassu.tracto.service.api;

import com.scribassu.tracto.dto.GroupNumbersDto;
import com.scribassu.tracto.entity.schedule.EducationForm;
import com.scribassu.tracto.entity.schedule.StudentGroup;
import com.scribassu.tracto.repository.StudentGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    public GroupNumbersDto getGroupNumbersByDepartmentUrlAndEducationFormAndCourse(String url,
                                                                                   EducationForm educationForm,
                                                                                   String course) {
        if (url.equals("cre")) {
            List<StudentGroup> groups = studentGroupRepository.findByDepartmentUrlAndEducationForm(url, educationForm);
            return new GroupNumbersDto(
                    groups
                            .stream()
                            .filter(s ->
                                    String.valueOf(s.getGroupNumberRus().charAt(s.getGroupNumberRus().length() - 3))
                                            .equalsIgnoreCase(course))
                            .map(StudentGroup::getGroupNumberRus).collect(Collectors.toList()),
                    url,
                    educationForm.name()
            );
        } else if (url.equals("kgl")) {
            List<StudentGroup> groups = studentGroupRepository.findByDepartmentUrlAndEducationForm(url, educationForm);
            return new GroupNumbersDto(
                    groups
                            .stream()
                            .filter(s -> String.valueOf(s.getGroupNumberRus().charAt(1)).equalsIgnoreCase(course))
                            .map(StudentGroup::getGroupNumberRus).collect(Collectors.toList()),
                    url,
                    educationForm.name()
            );
        } else {
            List<StudentGroup> groups = studentGroupRepository.findByDepartmentUrlAndEducationFormAndCourse(
                    url,
                    educationForm,
                    course
            );
            return new GroupNumbersDto(
                    groups.stream().map(StudentGroup::getGroupNumberRus).collect(Collectors.toList()),
                    url,
                    educationForm.name()
            );
        }
    }

    public GroupNumbersDto getOtherGroupNumbersByDepartmentUrlAndEducationForm(String url,
                                                                               EducationForm educationForm) {
        List<StudentGroup> groups = studentGroupRepository.findByDepartmentUrlAndEducationForm(url, educationForm);
        return new GroupNumbersDto(
                groups
                        .stream()
                        .filter(s -> !Character.isDigit(s.getGroupNumberRus().charAt(0)))
                        .map(StudentGroup::getGroupNumberRus)
                        .collect(Collectors.toList()),
                url,
                educationForm.name()
        );
    }
}
