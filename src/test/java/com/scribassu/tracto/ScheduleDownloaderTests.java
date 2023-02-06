package com.scribassu.tracto;

import com.scribassu.tracto.dto.xml.ScheduleXml;
import com.scribassu.tracto.service.downloader.ExamPeriodScheduleDownloaderImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
public class ScheduleDownloaderTests {

    @Autowired
    private ExamPeriodScheduleDownloaderImpl scheduleDownloader;

    @Value("${tracto.download-schedule.url}")
    private String url;

    @Test
    public void contextLoads() {
        assertThat(scheduleDownloader).isNotNull();
    }

    @Test
    public void downloadKntSchedule() throws JAXBException {
        String schedule = scheduleDownloader.downloadSchedule(url + "knt");
        StringReader stringReader = new StringReader(schedule);

        JAXBContext jaxbContext = JAXBContext.newInstance(ScheduleXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ScheduleXml scheduleXml = (ScheduleXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(schedule);
        assertNotNull(scheduleXml);
    }
}
