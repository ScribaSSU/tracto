package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "schedule")
public class ScheduleXml {

    @XmlElement(name = "group")
    public List<GroupXml> groups;
}
