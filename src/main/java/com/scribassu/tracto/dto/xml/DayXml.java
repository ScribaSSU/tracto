package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "day")
public class DayXml {

    @XmlAttribute(name = "id")
    public int id;

    @XmlElement(name = "lessons")
    public LessonsXml lessons;
}
