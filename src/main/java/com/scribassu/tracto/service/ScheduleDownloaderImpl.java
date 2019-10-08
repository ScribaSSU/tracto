package com.scribassu.tracto.service;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Component
public class ScheduleDownloaderImpl implements ScheduleDownloader {

    @Override
    public String downloadSchedule(String department, String scheduleType, String group, boolean isSession) {
        //LOGGER.info("Start download schedule for group " + group);
        String schedule;
        String file;

        if(scheduleType.equalsIgnoreCase("teacher")) {
            //group - teacher's schedule id
            schedule = "https://www.sgu.ru/teacher/" + group;
            file = "schedule_" + scheduleType + "_" + group + ".xls";
        }
        else {
            schedule = "https://www.sgu.ru/schedule/" + department + "/" + scheduleType + "/" + group;
            file = "schedule_" + scheduleType + "_" + group + ".xls";
        }

        if(isSession) {
            schedule = schedule + "/session";
        }
        else {
            schedule = schedule + "/lesson";
        }

        try {
            URL url = new URL(schedule);
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
