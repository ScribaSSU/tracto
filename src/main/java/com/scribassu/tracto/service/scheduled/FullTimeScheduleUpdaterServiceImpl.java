package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.ScheduleDownloader;
import com.scribassu.tracto.service.parser.FullTimeScheduleParserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FullTimeScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;

    private final ScheduleDownloader scheduleDownloader;

    private final FullTimeScheduleParserImpl fullTimeScheduleParser;

    @Autowired
    public FullTimeScheduleUpdaterServiceImpl(DepartmentRepository departmentRepository,
                                              ScheduleDownloader scheduleDownloader,
                                              FullTimeScheduleParserImpl fullTimeScheduleParser) {
        this.departmentRepository = departmentRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeScheduleParser = fullTimeScheduleParser;
    }

    @Scheduled(cron = "${tracto.download-schedule.full-time.time-update}")
    public void updateSchedule() {
        log.info("START to parse full-time schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            String departmentURL = department.getURL();
            String schedule = scheduleDownloader.downloadFullTimeSchedule(departmentURL);
            log.info("Download full-time schedule of " + departmentURL);
            ScheduleParserStatus status = fullTimeScheduleParser.parseSchedule(schedule, departmentURL);
            log.info(status.getSchedule() + " " + status.getStatus());
        }
        long end = System.currentTimeMillis();
        log.info("Time: " + ((end - start) / 1000) + " s");
        log.info("DOOOOOOOOOOOOOOOOOOOOONE parsing full time schedule");
    }
}
