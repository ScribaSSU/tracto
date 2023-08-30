package com.scribassu.tracto.entity.schedule;

public enum LessonType {
    LECTURE("лек."),
    PRACTICE("пр."),
    LABORATORY("лаб.");

    private String type;

    public String getType() {
        return type;
    }

    LessonType(String type) {
        this.type = type;
    }
}