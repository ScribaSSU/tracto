package com.scribassu.tracto.entity.schedule;

public enum WeekType {
    NOM("чис."),
    DENOM("знам."),
    FULL("");

    private String type;

    public String getType() { return type; }

    public static WeekType fromType(String type) {
        for(WeekType wt : WeekType.values()) {
            if(wt.type.equalsIgnoreCase(type)) {
                return wt;
            }
        }
        return FULL;
    }

    WeekType(String type) { this.type = type; }
}
