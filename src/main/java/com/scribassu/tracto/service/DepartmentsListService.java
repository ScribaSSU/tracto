package com.scribassu.tracto.service;

import com.scribassu.tracto.dto.web.DepartmentsListDto;
import com.scribassu.tracto.repository.DepartmentsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentsListService {
    private final DepartmentsListRepository departmentsListRepository;

    @Autowired
    public DepartmentsListService(DepartmentsListRepository departmentsListRepository) {
        this.departmentsListRepository = departmentsListRepository;
    }

    public DepartmentsListDto getAllDepartments() {
        return new DepartmentsListDto(departmentsListRepository.getDepartmentsList());
    }
}
