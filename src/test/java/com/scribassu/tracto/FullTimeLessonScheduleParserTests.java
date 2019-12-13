package com.scribassu.tracto;

import com.scribassu.tracto.entity.ScheduleParserStatus;
import com.scribassu.tracto.service.FullTimeLessonScheduleParserImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FullTimeLessonScheduleParserTests {

    @Autowired
    private FullTimeLessonScheduleParserImpl fullTimeLessonScheduleParser;

    @Test
    public void full() {
        ScheduleParserStatus s = fullTimeLessonScheduleParser.parseSchedule("knt", "do", "351");
    }
}
