package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.service.ScheduleDownloader;
import com.scribassu.tracto.service.ScheduleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleUpdaterService {

    private final DepartmentRepository departmentRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final ScheduleParser scheduleParser;

    @Autowired
    public ScheduleUpdaterService(DepartmentRepository departmentRepository,
                                  ScheduleDownloader scheduleDownloader,
                                  ScheduleParser scheduleParser) {
        this.departmentRepository = departmentRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.scheduleParser = scheduleParser;
    }

    @Scheduled(cron = "${tracto.time-update-schedule}")
    public void updateSchedules() {
        System.out.println("START");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for(Department department : departments) {
            String url = department.getURL();
            scheduleParser.parseSchedule(scheduleDownloader.downloadSchedule(url), url);
        }
        long end = System.currentTimeMillis();
        System.out.println(((end - start) / 1000));
        System.out.println("DOOOOOOOOOOOOOOOOOOOOONE");
    }
}
