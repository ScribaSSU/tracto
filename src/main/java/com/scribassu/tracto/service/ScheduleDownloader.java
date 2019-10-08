package com.scribassu.tracto.service;

public interface ScheduleDownloader {
    String downloadSchedule(String department, String groupType, String group, boolean isSession);
}
