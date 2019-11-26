package com.scribassu.tracto.service;

import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FullTimeLessonScheduleParserImpl implements ScheduleParser {

    private final ScheduleDownloader scheduleDownloader;
    private final FullTimeLessonRepository fullTimeLessonRepository;
    private final DepartmentRepository departmentRepository;
    private final DayRepository dayRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Autowired
    public FullTimeLessonScheduleParserImpl(ScheduleDownloaderImpl scheduleDownloader,
                                            FullTimeLessonRepository fullTimeLessonRepository,
                                            DepartmentRepository departmentRepository,
                                            DayRepository dayRepository,
                                            LessonTimeRepository lessonTimeRepository,
                                            ScheduleParserStatusRepository scheduleParserStatusRepository)
                                             {
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
        this.departmentRepository = departmentRepository;
        this.dayRepository = dayRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String departmentUrl, String scheduleType, String group) {
        return null;
    }
}
