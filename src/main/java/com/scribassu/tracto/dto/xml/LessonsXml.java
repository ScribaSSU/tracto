package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "lessons")
public class LessonsXml {

    @XmlElement(name = "lesson")
    public List<LessonXml> lessons;
}
