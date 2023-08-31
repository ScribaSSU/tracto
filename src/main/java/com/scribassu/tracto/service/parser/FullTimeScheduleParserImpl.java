package com.scribassu.tracto.service.parser;

import com.scribassu.tracto.dto.xml.*;
import com.scribassu.tracto.entity.ScheduleParserResult;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.entity.schedule.*;
import com.scribassu.tracto.mapper.XmlMapper;
import com.scribassu.tracto.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class FullTimeScheduleParserImpl implements ScheduleParser {

    private final FullTimeLessonRepository fullTimeLessonRepository;

    private final DayRepository dayRepository;

    private final LessonTimeRepository lessonTimeRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final DepartmentRepository departmentRepository;

    private final TeacherRepository teacherRepository;

    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    private final XmlMapper xmlMapper;

    private final Map<String, String> scheduleParsingResultMap = new HashMap<>();

    @Override
    public ScheduleParserResult parseSchedule(String schedule, String departmentUrl) {
        ScheduleParserResult scheduleParserResult;
        try {
            StringReader stringReader = new StringReader(schedule);
            JAXBContext jaxbContext = JAXBContext.newInstance(ScheduleXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ScheduleXml scheduleXml = (ScheduleXml) unmarshaller.unmarshal(stringReader);

            scheduleParsingResultMap.put("scheduleType", ScheduleType.FULL_TIME.name());
            scheduleParsingResultMap.put("department", departmentUrl);

            // TODO drop almost all database and fill it with new values?
            fullTimeLessonRepository.deleteByDepartmentUrl(departmentUrl);
            //studentGroupRepository.deleteByEducationForm(EducationForm.DO);

            parseGroups(scheduleXml.groups, departmentUrl);
        } catch (Exception e) {
            e.printStackTrace();
            scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
        } finally {
            scheduleParserResult = new ScheduleParserResult(scheduleParsingResultMap);
            scheduleParserStatusRepository.save(scheduleParserResult);
            scheduleParsingResultMap.clear();
        }

        return scheduleParserResult;
    }

    private void parseGroups(List<GroupXml> groups, String departmentUrl) {
        Department department = departmentRepository.findByUrl(departmentUrl);

        if (department == null) {
            scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
            throw new IllegalArgumentException("Get a wrong department during parse days for full-time lessons!");
        }

        for (GroupXml group : groups) {
            EducationForm educationForm = xmlMapper.toEducationForm(group.eduForm);
            StudentGroup studentGroup = studentGroupRepository.findByNumberAndEducationFormAndDepartment(group.numberRus.trim(), educationForm, department);
            if (null == studentGroup) {
                var newStudentGroup = xmlMapper.toStudentGroup(group, department);
                newStudentGroup = studentGroupRepository.save(newStudentGroup);
                log.info("Save student group {}", newStudentGroup);
                studentGroup = newStudentGroup;
            }
            if (studentGroup.getEducationForm().equals(EducationForm.DO)) {
                parseDays(group.days, studentGroup);
            }
        }
    }

    private void parseDays(List<DayXml> days, StudentGroup studentGroup) {
        scheduleParsingResultMap.put("educationForm",  studentGroup.getEducationForm().name());
        scheduleParsingResultMap.put("studentGroup", studentGroup.getGroupNumberRus());
        for (DayXml d : days) {
            Day day = dayRepository.findByDayNumber(d.id);

            if (day == null) {
                scheduleParsingResultMap.put("status", ScheduleParserStatus.ERROR.name());
                throw new IllegalArgumentException("Get a wrong day during parse days for full-time lessons!");
            }

            LessonsXml lessonsXml = d.lessons;
            if (!CollectionUtils.isEmpty(lessonsXml.lessons)) {
                parseLessons(lessonsXml.lessons, day, studentGroup);
            }
        }
    }

    private void parseLessons(List<LessonXml> lessons, Day day, StudentGroup studentGroup) {
        LessonTime lessonTime;
        Teacher teacher;

        if (isFromCollegeCre(studentGroup)) {
            for (LessonXml lessonXml : lessons) {
                lessonTime = lessonTimeRepository.findByLessonNumber(collegeCreLessonNumber(lessonXml));
                teacher = parseTeacher(lessonXml.teacher);

                var lesson = xmlMapper.toFullTimeLesson(lessonXml, day, lessonTime, teacher, studentGroup);
                fullTimeLessonRepository.save(lesson);
            }
        } else if (isFromCollegeKgl(studentGroup)) {
            for (LessonXml lessonXml : lessons) {
                lessonTime = lessonTimeRepository.findByLessonNumber(collegeKglLessonNumber(lessonXml));
                teacher = parseTeacher(lessonXml.teacher);

                var lesson = xmlMapper.toFullTimeLesson(lessonXml, day, lessonTime, teacher, studentGroup);
                fullTimeLessonRepository.save(lesson);
            }
        } else {
            for (LessonXml lessonXml : lessons) {
                lessonTime = lessonTimeRepository.findByLessonNumber(lessonXml.number);
                teacher = parseTeacher(lessonXml.teacher);

                var lesson = xmlMapper.toFullTimeLesson(lessonXml, day, lessonTime, teacher, studentGroup);
                fullTimeLessonRepository.save(lesson);
            }
        }
        scheduleParsingResultMap.put("status", ScheduleParserStatus.OK.name());
    }

    private boolean isFromCollegeCre(StudentGroup studentGroup) {
        return studentGroup.getDepartment().getUrl().equals("cre");
    }

    private boolean isFromCollegeKgl(StudentGroup studentGroup) {
        return studentGroup.getDepartment().getUrl().equals("kgl");
    }

    private int collegeCreLessonNumber(LessonXml lessonXml) {
        return lessonXml.number * 10 + lessonXml.number;
    }

    private int collegeKglLessonNumber(LessonXml lessonXml) {
        return lessonXml.number * 100 + lessonXml.number;
    }

    private Teacher parseTeacher(TeacherXml teacherXml) {
        List<Teacher> teacherList = teacherRepository.findBySurnameAndNameAndPatronymic(teacherXml.lastname, teacherXml.name, teacherXml.patronymic);

        if (teacherList.size() > 1) {
            log.warn("Find more than 1 teacher for surname {}, name {}, patronymic {}", teacherXml.lastname, teacherXml.name, teacherXml.patronymic);
        }

        if (CollectionUtils.isEmpty(teacherList)) {
            var teacher = xmlMapper.toTeacher(teacherXml);
            teacherRepository.save(teacher);
            return teacher;
        } else {
            return teacherList.get(0);
        }
    }
}
