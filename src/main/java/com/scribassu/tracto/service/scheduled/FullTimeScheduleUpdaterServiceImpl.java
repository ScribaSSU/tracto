package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.downloader.FullTimeScheduleDownloaderImpl;
import com.scribassu.tracto.service.downloader.ScheduleDownloader;
import com.scribassu.tracto.service.parser.FullTimeScheduleParserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FullTimeScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;

    private final FullTimeScheduleDownloaderImpl fullTimeScheduleDownloader;

    private final FullTimeScheduleParserImpl fullTimeScheduleParser;

    @Autowired
    public FullTimeScheduleUpdaterServiceImpl(DepartmentRepository departmentRepository,
                                              FullTimeScheduleDownloaderImpl fullTimeScheduleDownloader,
                                              FullTimeScheduleParserImpl fullTimeScheduleParser) {
        this.departmentRepository = departmentRepository;
        this.fullTimeScheduleDownloader = fullTimeScheduleDownloader;
        this.fullTimeScheduleParser = fullTimeScheduleParser;
    }

    @Scheduled(cron = "${tracto.download-schedule.full-time.time-update}")
    public void updateSchedule() {
        log.info("START to parse full-time schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            String departmentURL = department.getURL();
            String schedule = fullTimeScheduleDownloader.downloadSchedule(departmentURL);
            log.info("Download full-time schedule of " + departmentURL);
            ScheduleParserStatus status = fullTimeScheduleParser.parseSchedule(schedule, departmentURL);
            log.info(status.getSchedule() + " " + status.getStatus());
        }
        long end = System.currentTimeMillis();
        log.info("Time: " + ((end - start) / 1000) + " s");
        log.info("DOOOOOOOOOOOOOOOOOOOOONE parsing full time schedule");
    }
}
