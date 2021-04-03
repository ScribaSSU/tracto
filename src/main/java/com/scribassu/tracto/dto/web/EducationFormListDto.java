package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.EducationForm;
import lombok.Data;

@Data
public class EducationFormListDto {
    String[] shortForms = new String[3];
    String[] fullForms = new String[3];

    public EducationFormListDto() {
        shortForms[0] = EducationForm.DO.toString();
        shortForms[1] = EducationForm.ZO.toString();
        shortForms[2] = EducationForm.VO.toString();
        EducationForm[] ff2 = EducationForm.values();
        for (int i = 0; i < 3; i++) {
            fullForms[i] = ff2[i].getGroupType();
        }
    }
}

