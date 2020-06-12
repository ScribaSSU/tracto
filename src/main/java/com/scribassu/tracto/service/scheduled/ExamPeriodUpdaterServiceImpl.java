package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import com.scribassu.tracto.service.downloader.ScheduleDownloader;
import com.scribassu.tracto.service.parser.ExamPeriodScheduleParserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExamPeriodUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final ExamPeriodScheduleParserImpl sessionParser;

    @Value("${tracto.download-schedule.exam-period-url}")
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

    @Scheduled(cron = "${tracto.time-update-exam-period}")
    public void updateSchedule() {
        log.info("START to parse exam period schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for(Department department : departments) {
            String departmentURL = department.getURL();
            List<StudentGroup> studentGroups = studentGroupRepository.findByDepartmentUrl(departmentURL);
            for(StudentGroup studentGroup : studentGroups) {
                ScheduleParserStatus status = sessionParser.parseSchedule(scheduleDownloader.downloadSchedule(
                        String.format(
                                sessionUrl,
                                departmentURL,
                                studentGroup.getEducationForm().toString().toLowerCase(),
                                formatGroupNumber(studentGroup.getGroupNumber())
                        )
                ), departmentURL);
                log.info(status.getSchedule() + " " + status.getStatus());
            }
        }
        long end = System.currentTimeMillis();
        log.info("Time: " + ((end - start) / 1000) + " s");
        log.info("DOOOOOOOOOOOOOOOOOOOOONE parsing exam period schedule");
    }

    private String formatGroupNumber(String groupNumber) {
        String a = groupNumber
                .replace(" ", "%20")
                .replace("(", "%28")
                .replace(")", "%29")
                .replace("+", "%2B");
        return a;
    }
}
