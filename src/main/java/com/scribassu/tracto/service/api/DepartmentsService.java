package com.scribassu.tracto.service.api;

import com.scribassu.tracto.dto.DepartmentsListDto;
import com.scribassu.tracto.mapper.HttpMapper;
import com.scribassu.tracto.repository.DepartmentsListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class DepartmentsService {
    private final DepartmentsListRepository departmentsListRepository;
    private final HttpMapper httpMapper;

    public DepartmentsListDto getAllDepartments() {
        return httpMapper.toDepartmentsListDto(departmentsListRepository.getDepartmentsList());
    }
}
