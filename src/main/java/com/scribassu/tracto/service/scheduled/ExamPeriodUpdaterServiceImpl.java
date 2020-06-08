package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import com.scribassu.tracto.service.downloader.ScheduleDownloader;
import com.scribassu.tracto.service.parser.ExamPeriodScheduleParserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamPeriodUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final ExamPeriodScheduleParserImpl sessionParser;

    @Value("${tracto.download-schedule.session-url}")
    private String sessionUrl;

    @Autowired
    public ExamPeriodUpdaterServiceImpl(DepartmentRepository departmentRepository,
                                        StudentGroupRepository studentGroupRepository,
                                        ScheduleDownloader scheduleDownloader,
                                        ExamPeriodScheduleParserImpl sessionParser) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.sessionParser = sessionParser;
    }

    @Scheduled(cron = "${tracto.time-update-session}")
    public void updateSchedule() {
        System.out.println("START");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for(Department department : departments) {
            String departmentURL = department.getURL();
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
