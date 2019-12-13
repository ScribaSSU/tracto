package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "departments")
public class DepartmentsXml {

    @XmlElement(name = "department")
    public List<DepartmentXml> departments;

}
