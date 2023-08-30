package com.scribassu.tracto.properties;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "tracto.download-schedule.extramural")
@NoArgsConstructor
@ConstructorBinding
public class ExtramuralDownloadScheduleProperties extends AbstractDownloadScheduleProperties {

    public ExtramuralDownloadScheduleProperties(String path,
                                                String timeUpdate,
                                                int connectionTimeout,
                                                int readTimeout,
                                                int writeTimeout) {
        super(path, timeUpdate, connectionTimeout, readTimeout, writeTimeout);
    }
}