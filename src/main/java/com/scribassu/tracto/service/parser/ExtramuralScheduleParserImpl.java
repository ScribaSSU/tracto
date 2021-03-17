package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.domain.ExamPeriodMonth;
import com.scribassu.tracto.domain.ExtramuralEventType;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.domain.ExtramuralEvent;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ExamPeriodMonthRepository;
import com.scribassu.tracto.repository.ExtramuralEventRepository;
import com.scribassu.tracto.repository.LessonTimeRepository;
import com.scribassu.tracto.repository.ScheduleParserStatusRepository;
import com.scribassu.tracto.repository.StudentGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExtramuralScheduleParserImpl implements ScheduleParser {

    private static final String PAGE_TITLE_CLASS = "page-title";
    private static final String NOT_SPECIFIED = "Не указано";

    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final ExamPeriodMonthRepository examPeriodMonthRepository;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;
    private final ExtramuralEventRepository extramuralEventRepository;

    @Autowired
    public ExtramuralScheduleParserImpl(StudentGroupRepository studentGroupRepository,
                                        DepartmentRepository departmentRepository,
                                        LessonTimeRepository lessonTimeRepository,
                                        ExamPeriodMonthRepository examPeriodMonthRepository,
                                        ScheduleParserStatusRepository scheduleParserStatusRepository,
                                        ExtramuralEventRepository extramuralEventRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.departmentRepository = departmentRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.examPeriodMonthRepository = examPeriodMonthRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
        this.extramuralEventRepository = extramuralEventRepository;
    }


    @Override
    public ScheduleParserStatus parseSchedule(String schedule, String departmentURL) {
        ScheduleParserStatus status = new ScheduleParserStatus();
        Document document = Jsoup.parse(schedule);
        Elements title = document.getElementsByClass(PAGE_TITLE_CLASS);
        Element pageTitle = title.get(0);
        Department department = departmentRepository.findByURL(departmentURL);
        StudentGroup studentGroup = getStudentGroupByPageTitleText(pageTitle.text(), department);

        if (studentGroup == null) {
            log.error("Fail to find some student group for " + departmentURL);
            status.setStatus("fail no group");
            status.setSchedule("s-unknown" + "-" + departmentURL);
        } else {
            try {
                Element div = document.getElementById("schedule_page");
                if (div == null) {
                    log.error("Schedule page is empty for group " + studentGroup);
                    status.setStatus("fail no schedule");
                    status.setSchedule("s-" + studentGroup.getGroupNumber() + "-" + departmentURL);
                    return status;
                }
                Element sessionTable = document.getElementsByTag("table").first();
                if (sessionTable == null) {
                    log.error("Schedule page does not contain schedule table " + studentGroup);
                    status.setStatus("fail no schedule");
                    status.setSchedule("s-" + studentGroup.getGroupNumber() + "-" + departmentURL);
                    return status;
                }
                Elements trs = sessionTable.child(0).children();

                extramuralEventRepository.deleteByStudentGroup(studentGroup);
                ExtramuralEvent extramuralEvent = null;

                for (Element tr : trs) {
                    extramuralEvent = new ExtramuralEvent();
                    extramuralEvent.setStudentGroup(studentGroup);
                    extramuralEvent.setDepartment(department);
                    Elements tds = tr.children();
                    String time = tds.get(1).text();
                    String[] infos = tds.get(2).outerHtml()
                            .replaceAll("<td>", "")
                            .replaceAll("</td>", "")
                            .split("<br>");
                    String[] date = tds.get(0).text().split(" ");

                    if (date[0].startsWith("0")) {
                        date[0] = date[0].substring(1);
                    }
                    int day = Integer.parseInt(date[0]);

                    ExamPeriodMonth examPeriodMonth = examPeriodMonthRepository.findByRusGenitive(date[1]).get(0);
                    String year = date[2];

                    extramuralEvent.setDay(day);
                    extramuralEvent.setMonth(examPeriodMonth);
                    extramuralEvent.setYear(year);

                    String[] times = time.split("-");
                    int startHour = Integer.parseInt(times[0].split(":")[0]);
                    int startMinute = Integer.parseInt(times[0].split(":")[1]);
                    int endHour = -1;
                    int endMinute = -1;
                    if (times.length == 2) {
                        endHour = Integer.parseInt(times[1].split(":")[0]);
                        endMinute = Integer.parseInt(times[1].split(":")[1]);
                    }
                    extramuralEvent.setStartHour(startHour);
                    extramuralEvent.setStartMinute(startMinute);
                    extramuralEvent.setEndHour(endHour);
                    extramuralEvent.setEndMinute(endMinute);

                    List<ExtramuralEvent> possibleEvents = new ArrayList<>();
                    for (String info : infos) {
                        String[] strings;
                        if (info.contains(": ")) {
                            strings = info.split(": ");
                        } else if (info.contains(":")) {
                            strings = info.split(":");
                        } else {
                            continue;
                        }
                        if (" Преподаватель".equalsIgnoreCase(strings[0])
                                || "преподаватель".equalsIgnoreCase(strings[0])) {
                            if (strings.length > 1) {
                                extramuralEvent.setTeacher(strings[1]);
                            } else {
                                extramuralEvent.setTeacher(NOT_SPECIFIED);
                            }
                        } else if (" Место проведения".equalsIgnoreCase(strings[0])
                                || "Место проведения".equalsIgnoreCase(strings[0])) {
                            if (strings.length > 1) {
                                extramuralEvent.setPlace(strings[1]);
                            } else {
                                extramuralEvent.setPlace(NOT_SPECIFIED);
                            }
                        } else {
                            if (extramuralEvent.getEventType() != null) {
                                possibleEvents.add(extramuralEvent);
                                extramuralEvent = extramuralEvent.clone();
                            }

                            ExtramuralEventType eventType = ExtramuralEventType.getExtramuralEventType(strings[0]);
                            extramuralEvent.setEventType(eventType);
                            if (strings.length > 1) {
                                extramuralEvent.setName(strings[1]);
                            }

                        }
                    }
                    if (possibleEvents.isEmpty()) {
                        extramuralEvent = extramuralEventRepository.save(extramuralEvent);
                        if (extramuralEvent.getId() != null) {
                            status.setStatus("ok");
                        } else {
                            status.setStatus("fail to save");
                        }
                    } else {
                        possibleEvents.add(extramuralEvent);
                        long success = 0;
                        long fail = 0;
                        for (ExtramuralEvent e : possibleEvents) {
                            e = extramuralEventRepository.save(e);
                            if (e.getId() != null) {
                                success++;
                            } else {
                                fail++;
                            }
                        }
                        String message = (success != 0 ? ("ok-" + success) : "") + (fail != 0 ? ("failed to save-" + fail) : "");
                        status.setStatus(message);
                    }
                }
            } catch (Exception e) {
                log.error("Error while parsing schedule " + e);
                status.setStatus("fail with exception");
            }
            status.setSchedule("s-" + studentGroup.getGroupNumber() + "-" + departmentURL);
        }

        status = scheduleParserStatusRepository.save(status);


        return status;
    }

    private StudentGroup getStudentGroupByPageTitleText(String text, Department department) {
        EducationForm educationForm = EducationForm.ZO;
        text = text.replace(" группа", "");
        String[] educationForms = {"Дневное", "Заочное", "Вечернее"};
        if (text.startsWith(educationForms[0])) {
            educationForm = EducationForm.DO;
            text = text.replace(educationForms[0], "");
        }
        if (text.startsWith(educationForms[1])) {
            educationForm = EducationForm.ZO;
            text = text.replace(educationForms[1], "");
        }
        if (text.startsWith(educationForms[2])) {
            educationForm = EducationForm.VO;
            text = text.replace(educationForms[2], "");
        }

        text = text.replace("отделение:", "");
        String groupNumber = text.trim();

        return studentGroupRepository.findByNumberAndEducationFormAndDepartment(
                groupNumber,
                educationForm,
                department
        );
    }
}
