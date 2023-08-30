package com.scribassu.tracto.properties;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "tracto.download-schedule.exam-period")
@NoArgsConstructor
@ConstructorBinding
public class ExamPeriodDownloadScheduleProperties extends AbstractDownloadScheduleProperties {

    public ExamPeriodDownloadScheduleProperties(String path,
                                                String timeUpdate,
                                                int connectionTimeout,
                                                int readTimeout,
                                                int writeTimeout) {
        super(path, timeUpdate, connectionTimeout, readTimeout, writeTimeout);
    }
}