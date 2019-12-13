package com.scribassu.tracto.xmlparser;

import com.scribassu.tracto.dto.xml.*;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DepartmentXmlParserTests {
    private final String departmentXml = "<department id=\"bf\" name=\"Биологический факультет\" grid_type_id=\"1\">\n" +
            "<titletext>\n" +
            "У студентов 4 курса, направления Биология (421, 422 и 423 группы) смена расписания с 25 ноября. " +
            "Смотрите новое расписание на сайте. C 18 по 23 ноября - неделя по знаменателю (чётная) с 25 по 30 ноября - неделя по числителю (нечётная)\n" +
            "</titletext>\n" +
            "</department>";
    private final String departmentsXml = "<departments>" + departmentXml + "</departments>";
    private final String id = "bf";
    private final String name = "Биологический факультет";
    private final int grid_type_id = 1;
    private final String titletext = "У студентов 4 курса, направления Биология (421, 422 и 423 группы) смена расписания с 25 ноября. " +
            "Смотрите новое расписание на сайте. C 18 по 23 ноября - неделя по знаменателю (чётная) с 25 по 30 ноября - неделя по числителю (нечётная)";
    private final String nameDepartment = "Биологический факультет";


    @Test
    public void testDepartment() throws JAXBException {
        StringReader stringReader = new StringReader(departmentXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeacherXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DepartmentXml department = (DepartmentXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(department);
        assertEquals(id, department.id);
        assertEquals(name, department.name);
        assertEquals(grid_type_id, department.grid_type_id);
        assertEquals(titletext, department.titletext);
    }

    @Test
    public void testDepartments() throws JAXBException {
        StringReader stringReader = new StringReader(departmentsXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeacherXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DepartmentsXml departments = (DepartmentsXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(departments);
        assertNotNull(departments.departments);
        assertEquals(1, departments.departments.size());
        assertEquals(nameDepartment, departments.departments.get(0).name);
    }


}
