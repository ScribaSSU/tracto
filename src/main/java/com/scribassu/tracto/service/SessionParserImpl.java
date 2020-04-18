package com.scribassu.tracto.service;

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

        return null;
    }
}
