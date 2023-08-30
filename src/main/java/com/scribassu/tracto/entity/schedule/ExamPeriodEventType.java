package com.scribassu.tracto.entity.schedule;

public enum ExamPeriodEventType {
    MIDTERM("Зачет"),
    MIDTERM_WITH_MARK("Дифференцированный зачет"),
    EXAM("Экзамен"),
    CONSULTATION("Консультация");

    private String type;

    public String getType() { return type; }

    ExamPeriodEventType(String type) { this.type = type; }
}
