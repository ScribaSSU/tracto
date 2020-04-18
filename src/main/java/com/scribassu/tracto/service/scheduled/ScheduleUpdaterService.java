package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import com.scribassu.tracto.service.FullTimeScheduleParserImpl;
import com.scribassu.tracto.service.ScheduleDownloader;
import com.scribassu.tracto.service.SessionParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleUpdaterService {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final FullTimeScheduleParserImpl fullTimeScheduleParser;
    private final SessionParserImpl sessionParser;

    @Value("${tracto.download-schedule.url}")
    private String fullTimeScheduleUrl;

    @Value("${tracto.download-schedule.session-url}")
    private String sessionUrl;

    @Autowired
    public ScheduleUpdaterService(DepartmentRepository departmentRepository,
                                  StudentGroupRepository studentGroupRepository,
                                  ScheduleDownloader scheduleDownloader,
                                  FullTimeScheduleParserImpl fullTimeScheduleParser,
                                  SessionParserImpl sessionParser) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.fullTimeScheduleParser = fullTimeScheduleParser;
        this.sessionParser = sessionParser;
    }

    @Scheduled(cron = "${tracto.time-update-schedule}")
    public void updateSchedules() {
        System.out.println("START");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for(Department department : departments) {
            String departmentURL = department.getURL();

            //parse full time schedule
            fullTimeScheduleParser.parseSchedule(
                    scheduleDownloader.downloadSchedule(fullTimeScheduleUrl + departmentURL),
                    departmentURL);

            //parse session
            List<StudentGroup> studentGroups = studentGroupRepository.findByDepartmentUrl(departmentURL);
            for(StudentGroup studentGroup : studentGroups) {
                sessionParser.parseSchedule(scheduleDownloader.downloadSchedule(
                        String.format(
                                sessionUrl,
                                departmentURL,
                                studentGroup.getEducationForm().toString().toLowerCase(),
                                studentGroup.getGroupNumber()
                        )
                ), departmentURL);
            }

        }
        long end = System.currentTimeMillis();
        System.out.println(((end - start) / 1000));
        System.out.println("DOOOOOOOOOOOOOOOOOOOOONE");
    }
}
