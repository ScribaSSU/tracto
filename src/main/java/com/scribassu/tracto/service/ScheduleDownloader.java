package com.scribassu.tracto.service;

public interface ScheduleDownloader {
    String downloadSchedule(String department, String scheduleType, String group, boolean isSession);
}
