package com.scribassu.tracto;

import com.scribassu.tracto.dto.xml.ScheduleXml;
import com.scribassu.tracto.service.ScheduleDownloaderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles(profiles = "dev")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleDownloaderTests {

    @Autowired
    private ScheduleDownloaderImpl scheduleDownloader;

    @Value("${tracto.download-schedule.url}")
    private String url;

    @Value("${tracto.download-schedule.auth-header}")
    private String authHeader;

    @Test
    public void contextLoads() {
        assertThat(scheduleDownloader).isNotNull();
    }

    @Test
    public void downloadKntSchedule() throws JAXBException {
        String schedule = scheduleDownloader.downloadSchedule("knt");
        StringReader stringReader = new StringReader(schedule);

        JAXBContext jaxbContext = JAXBContext.newInstance(ScheduleXml.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ScheduleXml scheduleXml = (ScheduleXml) unmarshaller.unmarshal(stringReader);

        assertNotNull(schedule);
        assertNotNull(scheduleXml);
    }
}
