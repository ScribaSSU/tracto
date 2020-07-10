package com.scribassu.tracto.xmlparser;

import com.scribassu.tracto.dto.xml.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles({"dev"})
@SpringBootTest
@RunWith(SpringRunner.class)
public class LessonXmlParserTests {

    private final String teacherXml = "<teacher id=\"99999\">\n" +
            "                        <lastname>Иванов</lastname>\n" +
            "                        <name>Иван</name>\n" +
            "                        <patronim>Иванович</patronim>\n" +
            "    <compiled_fio>Иванов Иван Иванович</compiled_fio>\n" +
            "                    </teacher>";

    private final String lessonXml = "<lesson type=\"practice\" weektype=\"full\" num=\"1\" updated=\"1567847064\" date_begin=\"\" date_end=\"\">\n" +
            "                    <name>Физическая культура</name>\n" +
            "                    <place>999 корп. спортзал</place>\n" +
            "                    <subgroup/>\n" +
            teacherXml + "</lesson>";

    private final String lessonsXml = "<lessons>" + lessonXml + lessonXml + "</lessons>";

    private final String dayXml = "<day id=\"1\">" + lessonsXml + "</day>";

    private final String groupXml = "<group inner_group_id=\"450\" number=\"131\" number_rus=\"131\" edu_form=\"0\" grp_type=\"0\">" +
            dayXml + "</group>";

    private final String scheduleXml = "<schedule>" + groupXml + "</schedule>";

    private final int idTeacher = 99999;

    private final String lastNameTeacher = "Иванов";

    private final String nameTeacher = "Иван";

    private final String patronymicTeacher = "Иванович";

    private final String compiledFioTeacher = "Иванов Иван Иванович";

    private final String typeLesson = "practice";

    private final String weekTypeLesson = "full";

    private final int numLesson = 1;

    private final String updatedLesson = "1567847064";

    private final String dateBeginLesson = "";

    private final String dateEndLesson = "";

    private final String nameLesson = "Физическая культура";

    private final String placeLesson = "999 корп. спортзал";

    private final String subgroupLesson = "";

    private final int innerGroupIdGroup = 450;

    private final String numberGroup = "131";

    private final String numberRusGroup = "131";

    private final int eduFormGroup = 0;

    private final int groupTypeGroup = 0;

    @Test
    public void parseTeacher() throws JAXBException {
        StringReader stringReader = new StringReader(teacherXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeacherXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TeacherXml teacher = (TeacherXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(teacher);
        assertEquals(idTeacher, teacher.id);
        assertEquals(lastNameTeacher, teacher.lastname);
        assertEquals(nameTeacher, teacher.name);
        assertEquals(patronymicTeacher, teacher.patronymic);
        assertEquals(compiledFioTeacher, teacher.compiledFio);
    }

    @Test
    public void parseLesson() throws JAXBException {
        StringReader stringReader = new StringReader(lessonXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(LessonXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        LessonXml lesson = (LessonXml) unmarshaller.unmarshal(stringReader);
        TeacherXml teacher = lesson.teacher;

        assertNotNull(teacher);
        assertEquals(idTeacher, teacher.id);
        assertEquals(lastNameTeacher, teacher.lastname);
        assertEquals(nameTeacher, teacher.name);
        assertEquals(patronymicTeacher, teacher.patronymic);
        assertEquals(compiledFioTeacher, teacher.compiledFio);
        assertEquals(typeLesson, lesson.type);
        assertEquals(weekTypeLesson, lesson.weekType);
        assertEquals(numLesson, lesson.number);
        assertEquals(updatedLesson, lesson.updated);
        assertEquals(dateBeginLesson, lesson.dateBegin);
        assertEquals(dateEndLesson, lesson.dateEnd);
        assertEquals(nameLesson, lesson.name);
        assertEquals(placeLesson, lesson.place);
        assertEquals(subgroupLesson, lesson.subgroup);
    }

    @Test
    public void testLessons() throws JAXBException {
        StringReader stringReader = new StringReader(lessonsXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(LessonsXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        LessonsXml lessons = (LessonsXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(lessons);
        assertNotNull(lessons.lessons);
        assertEquals(2, lessons.lessons.size());
        assertEquals(nameLesson, lessons.lessons.get(0).name);
        assertEquals(nameLesson, lessons.lessons.get(1).name);
    }

    @Test
    public void testDay() throws JAXBException {
        StringReader stringReader = new StringReader(dayXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(DayXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DayXml day = (DayXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(day);
        assertNotNull(day.lessons);
        assertNotNull(day.lessons.lessons);
        assertEquals(1, day.id);
        assertEquals(2, day.lessons.lessons.size());
        assertEquals(nameLesson, day.lessons.lessons.get(0).name);
        assertEquals(nameLesson, day.lessons.lessons.get(1).name);
    }

    @Test
    public void testGroup() throws JAXBException {
        StringReader stringReader = new StringReader(groupXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(GroupXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        GroupXml group = (GroupXml) unmarshaller.unmarshal(stringReader);
        DayXml day = group.days.get(0);

        assertNotNull(group);
        assertNotNull(group.days);
        assertNotNull(day.lessons.lessons);
        assertEquals(innerGroupIdGroup, group.innerGroupId);
        assertEquals(numberGroup, group.number);
        assertEquals(numberRusGroup, group.numberRus);
        assertEquals(eduFormGroup, group.eduForm);
        assertEquals(groupTypeGroup, group.groupType);
        assertEquals(1, group.days.size());
        assertEquals(1, day.id);
        assertEquals(2, day.lessons.lessons.size());
        assertEquals(nameLesson, day.lessons.lessons.get(0).name);
        assertEquals(nameLesson, day.lessons.lessons.get(1).name);
    }

    @Test
    public void testSchedule() throws JAXBException {
        StringReader stringReader = new StringReader(scheduleXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(ScheduleXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ScheduleXml schedule = (ScheduleXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(schedule);
        assertNotNull(schedule.groups);
        assertEquals(1, schedule.groups.size());
        assertEquals(numberGroup, schedule.groups.get(0).number);
    }
}
