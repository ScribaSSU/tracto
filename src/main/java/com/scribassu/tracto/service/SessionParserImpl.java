package com.scribassu.tracto.service;

import com.scribassu.tracto.domain.ExamPeriodEvent;
import com.scribassu.tracto.entity.ScheduleParserStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class SessionParserImpl implements ScheduleParser {

    @Override
    public ScheduleParserStatus parseSchedule(String schedule, String department) {
        Document document = Jsoup.parse(schedule);
        Element sessionTable = document.getElementById("session");
        Elements trs = sessionTable.child(0).children();
        ExamPeriodEvent examPeriodEvent;
        for(Element tr : trs) {
            Elements tds = tr.children();
            if(tds.size() == 4) {
                examPeriodEvent = new ExamPeriodEvent();
                examPeriodEvent.setSubjectName(tds.get(3).text());
                System.out.println("SESSION ITEM BEGINS");
                for(Element td : tds) {
                    System.out.println("td4:" + td.text());
                }
            }
            if(tds.size() == 2) {
                for(Element td : tds) {
                    System.out.println("td2:" + td.text());
                }
            }
        }

        return null;
    }
}
