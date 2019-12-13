package com.scribassu.tracto.service;

import com.scribassu.tracto.entity.ScheduleParserStatus;

public interface ScheduleParser {

    ScheduleParserStatus parseSchedule(String schedule, String department);
}
