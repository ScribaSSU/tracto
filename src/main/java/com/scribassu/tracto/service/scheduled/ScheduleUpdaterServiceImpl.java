package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.parser.FullTimeScheduleParserImpl;
import com.scribassu.tracto.service.downloader.ScheduleDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final FullTimeScheduleParserImpl fullTimeScheduleParser;

    @Value("${tracto.download-schedule.url}")
    private String fullTimeScheduleUrl;

    @Autowired
    public ScheduleUpdaterServiceImpl(DepartmentRepository departmentRepository,
                                      ScheduleDownloader scheduleDownloader,
                                      FullTimeScheduleParserImpl fullTimeScheduleParser) {
        this.departmentRepository = departmentRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeScheduleParser = fullTimeScheduleParser;
    }

    @Scheduled(cron = "${tracto.time-update-schedule}")
    public void updateSchedule() {
        System.out.println("START");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for(Department department : departments) {
            String departmentURL = department.getURL();
            fullTimeScheduleParser.parseSchedule(
                    scheduleDownloader.downloadSchedule(fullTimeScheduleUrl + departmentURL),
                    departmentURL);
        }
        long end = System.currentTimeMillis();
        System.out.println(((end - start) / 1000));
        System.out.println("DOOOOOOOOOOOOOOOOOOOOONE");
    }
}
