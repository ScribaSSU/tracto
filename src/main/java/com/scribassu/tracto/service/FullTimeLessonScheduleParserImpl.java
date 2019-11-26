package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.*;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FullTimeLessonScheduleParserImpl implements ScheduleParser {

    private final ScheduleDownloader scheduleDownloader;
    private final FullTimeLessonRepository fullTimeLessonRepository;
    private final DepartmentRepository departmentRepository;
    private final DayRepository dayRepository;
    private final TimeRepository timeRepository;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Autowired
    public FullTimeLessonScheduleParserImpl(ScheduleDownloaderImpl scheduleDownloader,
                                            FullTimeLessonRepository fullTimeLessonRepository,
                                            DepartmentRepository departmentRepository,
                                            DayRepository dayRepository,
                                            TimeRepository timeRepository,
                                            ScheduleParserStatusRepository scheduleParserStatusRepository)
                                             {
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeLessonRepository = fullTimeLessonRepository;
        this.departmentRepository = departmentRepository;
        this.dayRepository = dayRepository;
        this.timeRepository = timeRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    @Override
    public ScheduleParserStatus parseSchedule(String departmentUrl, String scheduleType, String group) {
        return null;
    }
}
