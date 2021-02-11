package com.scribassu.tracto.service.parser;

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

@Slf4j
@Service
public class ExtramuralScheduleParserImpl implements ScheduleParser {

    private static final String PAGE_TITLE_CLASS = "page-title";

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

        StudentGroup studentGroup = getStudentGroupByPageTitleText(pageTitle.text(), departmentURL);

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
                    status.setSchedule("s-unknown" + "-" + departmentURL);
                    return status;
                }
                Element sessionTable = document.getElementsByTag("table").first();
                Elements trs = sessionTable.child(0).children();

                extramuralEventRepository.deleteByStudentGroup(studentGroup);
                ExtramuralEvent extramuralEvent = null;

                for (Element tr : trs) {
                    extramuralEvent = new ExtramuralEvent();
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

                    extramuralEvent.setLessonTime(lessonTimeRepository.findByLessonNumber(getLessonNumber(time)));

                    for (String info : infos) {
                        String[] strings = info.split(": ");
                        if (" Преподаватель".equalsIgnoreCase(strings[0])) {
                            extramuralEvent.setTeacher(strings[1]);
                        } else if (" Место проведения".equalsIgnoreCase(strings[0])) {
                            extramuralEvent.setPlace(strings[1]);
                        } else {
                            ExtramuralEventType eventType = ExtramuralEventType.getExtramuralEventType(strings[0]);
                            extramuralEvent.setEventType(eventType);
                            extramuralEvent.setName(strings[1]);
                        }
                    }
                    extramuralEvent = extramuralEventRepository.save(extramuralEvent);
                    if (extramuralEvent.getId() != null) {
                        status.setStatus("ok");
                    } else {
                        status.setStatus("fail to save");
                    }
                }
            } catch (Exception e) {
                status.setStatus("fail " + e);
            }
        }

        status = scheduleParserStatusRepository.save(status);


        return status;
    }

    private int getLessonNumber(String time) throws IllegalArgumentException {
        switch (time) {
            case "8:20-9:50":
                return 1;
            case "10:00-11:35":
                return 2;
            case "12:05-13:40":
                return 3;
            case "13:50-15:25":
                return 4;
            case "15:35-17:10":
                return 5;
            case "17:20-18:40":
                return 6;
            case "18:45-20:05":
                return 7;
            case "20:10-21:30":
                return 8;
            default:
                throw new IllegalArgumentException("Unknown lesson time " + time);
        }
    }

    private StudentGroup getStudentGroupByPageTitleText(String text, String departmentURL) {
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
                departmentRepository.findByURL(departmentURL)
        );
    }
}
