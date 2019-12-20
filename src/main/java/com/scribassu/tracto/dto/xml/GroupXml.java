package com.scribassu.tracto.dto.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "group")
public class GroupXml {

    @XmlAttribute(name = "inner_group_id")
    public int innerGroupId;

    @XmlAttribute(name = "number")
    public String number;

    @XmlAttribute(name = "number_rus")
    public String numberRus;

    @XmlAttribute(name = "edu_form")
    public int eduForm;

    @XmlAttribute(name = "grp_type")
    public int groupType;

    @XmlElement(name = "day")
    public List<DayXml> days;
}
