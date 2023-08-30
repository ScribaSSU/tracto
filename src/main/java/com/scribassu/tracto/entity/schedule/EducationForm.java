package com.scribassu.tracto.entity.schedule;

public enum EducationForm {
    DO("Дневная"),
    ZO("Заочная"),
    VO("Вечерняя");

    private String type;

    public String getGroupType() {
        return type;
    }

    public static EducationForm fromGroupType(String type) {
        for(EducationForm ef : EducationForm.values()) {
            if(ef.type.equalsIgnoreCase(type)) {
                return ef;
            }
        }
        return DO;
    }

    EducationForm(String type) {
        this.type = type;
    }
}
