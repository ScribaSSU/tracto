package com.scribassu.tracto.dto.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupNumbersDto {

    private List<String> groupNumbers;
    private String departmentUrl;
    private String educationForm;

    public GroupNumbersDto(List<String> groupNumbers,
                           String departmentUrl,
                           String educationForm) {
        this.groupNumbers = groupNumbers;
        this.departmentUrl = departmentUrl;
        this.educationForm = educationForm;
    }
}
