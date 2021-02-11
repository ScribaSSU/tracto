package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ScheduleParserStatusRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import com.scribassu.tracto.service.downloader.ScheduleDownloader;
import com.scribassu.tracto.service.parser.ExtramuralScheduleParserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class ExtramuralScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final ExtramuralScheduleParserImpl extramuralScheduleParser;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Value("${tracto.download-schedule.extramural-url}")
    private String extramuralUrl;

    @Autowired
    public ExtramuralScheduleUpdaterServiceImpl(DepartmentRepository departmentRepository,
                                                StudentGroupRepository studentGroupRepository,
                                                ScheduleDownloader scheduleDownloader,
                                                ExtramuralScheduleParserImpl extramuralScheduleParser,
                                                ScheduleParserStatusRepository scheduleParserStatusRepository) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.scheduleDownloader = scheduleDownloader;
        this.extramuralScheduleParser = extramuralScheduleParser;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    @Scheduled(cron = "${tracto.time-update-extramural}")
    public void updateSchedule() {
        log.info("START to parse extramural schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for (Department department : departments) {
            String departmentURL = department.getURL();
            List<StudentGroup> studentGroups = studentGroupRepository.findByDepartmentUrlAndEducationForm(departmentURL, EducationForm.ZO);
            for (StudentGroup studentGroup : studentGroups) {
                String html = scheduleDownloader.downloadSchedule(String.format(
                        extramuralUrl,
                        departmentURL,
                        studentGroup.getEducationForm().toString().toLowerCase(),
                        formatGroupNumber(studentGroup.getGroupNumber()))
                );
                ScheduleParserStatus status;
                if (!StringUtils.isEmpty(html)) {
                    status = extramuralScheduleParser.parseSchedule(html, departmentURL);
                } else {
                    status = new ScheduleParserStatus();
                    status.setSchedule("s-" + studentGroup.getGroupNumber() + "-" + departmentURL);
                    status.setStatus("fail to dwnld html");
                    status = scheduleParserStatusRepository.save(status);
                }
                log.info(status.getSchedule() + " " + status.getStatus());
            }
        }
        long end = System.currentTimeMillis();
        log.info("Time: " + ((end - start) / 1000) + " s");
        log.info("DOOOOOOOOOOOOOOOOOOOOONE parsing extramural schedule");
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
