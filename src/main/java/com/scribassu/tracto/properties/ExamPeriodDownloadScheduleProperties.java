package com.scribassu.tracto.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@NoArgsConstructor
@ConstructorBinding
public class ExamPeriodDownloadScheduleProperties {
    private String path;
    private String timeUpdate;
    private int connectionTimeout;
    private int readTimeout;
    private int writeTimeout;

    public ExamPeriodDownloadScheduleProperties(String path,
                                                String timeUpdate,
                                                int connectionTimeout,
                                                int readTimeout,
                                                int writeTimeout) {
        this.path = path;
        this.timeUpdate = timeUpdate;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }
}