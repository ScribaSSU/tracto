package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.entity.ScheduleParserResult;
import com.scribassu.tracto.entity.schedule.Department;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.ScheduleDownloader;
import com.scribassu.tracto.service.parser.FullTimeScheduleParserImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FullTimeScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;

    private final ScheduleDownloader scheduleDownloader;

    private final FullTimeScheduleParserImpl fullTimeScheduleParser;

    //TODO save time as metric
    @Override
    @Scheduled(cron = "${tracto.download-schedule.full-time.time-update}")
    public void updateSchedule() {
        log.info("Start to parse full-time schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            String departmentUrl = department.getUrl();
            String schedule = scheduleDownloader.downloadFullTimeSchedule(departmentUrl);
            log.info("Download full-time schedule of " + departmentUrl);
            ScheduleParserResult status = fullTimeScheduleParser.parseSchedule(schedule, departmentUrl);
            log.info("Finish parse full-time entry with status " + status);
        }
        long end = System.currentTimeMillis();
        log.info("Finish parsing full-time schedule, time: " + ((end - start) / 1000) + " s");
    }
}
