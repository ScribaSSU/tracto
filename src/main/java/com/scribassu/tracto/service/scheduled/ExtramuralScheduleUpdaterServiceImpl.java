package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.entity.ScheduleParserResult;
import com.scribassu.tracto.entity.schedule.Department;
import com.scribassu.tracto.entity.schedule.EducationForm;
import com.scribassu.tracto.entity.schedule.StudentGroup;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import com.scribassu.tracto.service.ScheduleDownloader;
import com.scribassu.tracto.service.parser.ExtramuralScheduleParserImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ExtramuralScheduleUpdaterServiceImpl implements ScheduleUpdater {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ScheduleDownloader scheduleDownloader;
    private final ExtramuralScheduleParserImpl extramuralScheduleParser;

    @Scheduled(cron = "${tracto.download-schedule.extramural.time-update}")
    public void updateSchedule() {
        log.info("Start to parse extramural schedule");
        long start = System.currentTimeMillis();
        List<Department> departments = departmentRepository.findAll();

        for (Department department : departments) {
            String departmentUrl = department.getUrl();
            List<StudentGroup> studentGroups = studentGroupRepository.findByDepartmentUrlAndEducationForm(departmentUrl, EducationForm.ZO);
            for (StudentGroup studentGroup : studentGroups) {
                String html = scheduleDownloader.downloadExtramuralSchedule(departmentUrl, studentGroup.getEducationForm().toString().toLowerCase(), formatGroupNumber(studentGroup.getGroupNumber()));
                ScheduleParserResult status = extramuralScheduleParser.parseSchedule(html, departmentUrl);
                log.info("Finish parse extramural entry with status " + status);
            }
        }
        long end = System.currentTimeMillis();
        log.info("Finish parsing extramural schedule, time: " + ((end - start) / 1000) + " s");
    }

    private String formatGroupNumber(String groupNumber) {
        return groupNumber
                .replace(" ", "%20")
                .replace("(", "%28")
                .replace(")", "%29")
                .replace("+", "%2B");
    }
}
