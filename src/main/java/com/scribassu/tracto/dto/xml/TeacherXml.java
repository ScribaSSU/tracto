package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teacher")
public class TeacherXml {

    @XmlAttribute(name = "id")
    public int id;

    @XmlElement(name = "lastname")
    public String lastname;

    @XmlElement(name = "name")
    public String name;

    @XmlElement(name = "patronim")
    public String patronymic;

    @XmlElement(name = "compiled_fio")
    public String compiledFio;
}
