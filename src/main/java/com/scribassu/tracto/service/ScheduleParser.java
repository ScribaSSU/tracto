package com.scribassu.tracto.service;

import com.scribassu.tracto.entity.ScheduleParserStatusEntity;

public interface ScheduleParser {

    /*
    schedule type - fulltime or extramural or teacher
     */
    ScheduleParserStatusEntity parseSchedule(String department, String scheduleType, String group);
}
