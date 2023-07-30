package com.scribassu.tracto.service;

import com.scribassu.tracto.dto.web.EducationFormListDto;
import org.springframework.stereotype.Service;

@Service
public class EducationFormListService {
    public EducationFormListDto getEducationFormList() {
        return new EducationFormListDto();
    }
}
