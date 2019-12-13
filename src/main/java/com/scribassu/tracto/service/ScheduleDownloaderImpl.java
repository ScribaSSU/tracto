package com.scribassu.tracto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
public class ScheduleDownloaderImpl implements ScheduleDownloader {

    @Value("${tracto.download-schedule.url}")
    private String url;

    private final String scheduleFilePrefix = "schedule_";
    private final String scheduleFileSuffix = ".xml";

    @Override
    public String downloadSchedule(String departmentUrl) {
        //LOGGER.info("Start download schedule for group " + group);
        String scheduleUrl = url + departmentUrl;
        String file = scheduleFilePrefix + departmentUrl + scheduleFileSuffix;

        try {
            URL url = new URL(scheduleUrl);
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            readableByteChannel.close();
            fileOutputStream.close();
            return file;
            //LOGGER.info("Finish download schedule for group " + group);
        }
        catch(IOException e) {
            //LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

        return "";
    }
}
