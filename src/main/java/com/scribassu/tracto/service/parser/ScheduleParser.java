package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.entity.ScheduleParserStatus;

public interface ScheduleParser {

    ScheduleParserStatus parseSchedule(String schedule, String department);
}
