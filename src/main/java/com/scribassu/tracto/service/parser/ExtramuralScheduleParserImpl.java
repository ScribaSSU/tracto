package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.entity.ScheduleParserResult;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.entity.schedule.*;
import com.scribassu.tracto.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ExtramuralScheduleParserImpl implements ScheduleParser {

    private static final String PAGE_TITLE_CLASS = "page-title";
    private static final String NOT_SPECIFIED = "Не указано";

    private final StudentGroupRepository studentGroupRepository;
    private final DepartmentRepository departmentRepository;
    private final ExamPeriodMonthRepository examPeriodMonthRepository;
    private final ScheduleParserStatusRepository scheduleParserStatusRepository;
    private final ExtramuralEventRepository extramuralEventRepository;

    private final Map<String, String> scheduleParsingResultMap = new HashMap<>();
// 2023-08-31 01:35:14,357 [http-nio-9001-exec-1] ERROR c.s.t.s.p.ExtramuralScheduleParserImpl - Error while parsing schedule org.springframework.dao.IncorrectResultSizeDataAccessException: query did not return a unique result: 2; nested exception is javax.persistence.NonUniqueResultException: query did not return a unique result: 2
    @Override
    public ScheduleParserResult parseSchedule(String schedule, String departmentUrl) {
        ScheduleParserResult scheduleParserResult;
        try {
            Document document = Jsoup.parse(schedule);
            Elements title = document.getElementsByClass(PAGE_TITLE_CLASS);
            Element pageTitle = title.get(0);
            Department department = departmentRepository.findByUrl(departmentUrl);
            StudentGroup studentGroup = getStudentGroupByPageTitleText(pageTitle.text(), department);

            scheduleParsingResultMap.put("scheduleType", ScheduleType.EXTRAMURAL.name());
            scheduleParsingResultMap.put("department", departmentUrl);

            if (studentGroup == null) {
                log.error("Fail to find some student group for " + departmentUrl);
                scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
            } else {
                scheduleParsingResultMap.put("educationForm", studentGroup.getEducationForm().name());
                scheduleParsingResultMap.put("studentGroup", studentGroup.getGroupNumberRus());
                try {
                    Element div = document.getElementById("schedule_page");
                    if (div == null) {
                        log.error("Schedule page is empty for group " + studentGroup);
                        scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
                    }
                    Element sessionTable = document.getElementsByTag("table").first();
                    if (sessionTable == null) {
                        log.error("Schedule page does not contain schedule table " + studentGroup);
                        scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
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
                                scheduleParsingResultMap.put("status", ScheduleParserStatus.OK.name());
                            } else {
                                scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
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
                            if (fail > 0) {
                                scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
                            } else {
                                scheduleParsingResultMap.put("status", ScheduleParserStatus.OK.name());
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("Error while parsing schedule " + e);
                    e.printStackTrace();
                    scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
                }
            }
        } catch(Exception e) {
            log.error("Error while parsing schedule " + e);
            e.printStackTrace();
            scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
        } finally {
            scheduleParserResult = new ScheduleParserResult(scheduleParsingResultMap);
            scheduleParserResult = scheduleParserStatusRepository.save(scheduleParserResult);
            scheduleParsingResultMap.clear();
        }

        return scheduleParserResult;
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
