package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.parser.FullTimeScheduleParserImpl;
import com.scribassu.tracto.service.downloader.ScheduleDownloader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FullTimeScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final FullTimeScheduleParserImpl fullTimeScheduleParser;

    @Value("${tracto.download-schedule.url}")
    private String fullTimeScheduleUrl;

    @Autowired
    public FullTimeScheduleUpdaterServiceImpl(DepartmentRepository departmentRepository,
                                              ScheduleDownloader scheduleDownloader,
                                              FullTimeScheduleParserImpl fullTimeScheduleParser) {
        this.departmentRepository = departmentRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeScheduleParser = fullTimeScheduleParser;
    }

    @Scheduled(cron = "${tracto.time-update-schedule}")
    public void updateSchedule() {
        log.info("START to parse full time schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for(Department department : departments) {
            String departmentURL = department.getURL();
            ScheduleParserStatus status = fullTimeScheduleParser.parseSchedule(
                    scheduleDownloader.downloadSchedule(fullTimeScheduleUrl + departmentURL),
                    departmentURL);
            log.info(status.getSchedule() + " " + status.getStatus());
        }
        long end = System.currentTimeMillis();
        log.info("Time: " + ((end - start) / 1000) + " s");
        log.info("DOOOOOOOOOOOOOOOOOOOOONE parsing full time schedule");
    }
}
