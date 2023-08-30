package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.entity.ScheduleParserResult;

public interface ScheduleParser {
    ScheduleParserResult parseSchedule(String schedule, String department);
}
