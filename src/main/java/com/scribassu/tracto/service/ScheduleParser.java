package com.scribassu.tracto.service;

import com.scribassu.tracto.entity.ScheduleParserStatus;

public interface ScheduleParser {

    /*
    schedule type - fulltime or extramural or teacher
     */
    ScheduleParserStatus parseSchedule(String department, String scheduleType, String group);
}
