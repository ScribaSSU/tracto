package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lesson")
public class LessonXml {

    @XmlAttribute(name = "type")
    public String type;

    @XmlAttribute(name = "weektype")
    public String weekType;

    @XmlAttribute(name = "num")
    public int number;

    @XmlAttribute(name = "updated")
    public String updated;

    @XmlAttribute(name = "date_begin")
    public String dateBegin;

    @XmlAttribute(name = "date_end")
    public String dateEnd;

    @XmlElement(name = "name")
    public String name;

    @XmlElement(name = "place")
    public String place;

    @XmlElement(name = "subgroup")
    public String subgroup;

    @XmlElement(name = "teacher")
    public TeacherXml teacher;
}
