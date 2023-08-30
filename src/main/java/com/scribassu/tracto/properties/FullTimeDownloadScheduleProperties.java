package com.scribassu.tracto.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "tracto.download-schedule.full-time")
@NoArgsConstructor
@ConstructorBinding
public class FullTimeDownloadScheduleProperties extends AbstractDownloadScheduleProperties {

    @Getter
    @Setter
    private String authHeader;

    public FullTimeDownloadScheduleProperties(String path,
                                              String authHeader,
                                              String timeUpdate,
                                              int connectionTimeout,
                                              int readTimeout,
                                              int writeTimeout) {
        super(path, timeUpdate, connectionTimeout, readTimeout, writeTimeout);
        this.authHeader = authHeader;
    }
}