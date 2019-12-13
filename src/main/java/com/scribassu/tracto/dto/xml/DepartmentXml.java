package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "department")
public class DepartmentXml {

    @XmlAttribute(name = "id")
    public String id;

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "grid_type_id")
    public int grid_type_id;

    @XmlElement(name = "titletext")
    public String titletext;

}
