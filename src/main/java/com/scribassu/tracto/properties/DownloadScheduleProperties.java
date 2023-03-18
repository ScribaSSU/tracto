package com.scribassu.tracto.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@NoArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "tracto.download-schedule")
public class DownloadScheduleProperties {
    private String baseUrl;
    private int queryTimeout;

    private int maxInMemorySize = 20 * 1024 * 1024;
    private FullTimeDownloadScheduleProperties fullTime;
    private ExamPeriodDownloadScheduleProperties examPeriod;
    private ExtramuralDownloadScheduleProperties extramural;

    public DownloadScheduleProperties(String baseUrl,
                                      int queryTimeout,
                                      int maxInMemorySize,
                                      FullTimeDownloadScheduleProperties fullTime,
                                      ExamPeriodDownloadScheduleProperties examPeriod,
                                      ExtramuralDownloadScheduleProperties extramural) {
        this.baseUrl = baseUrl;
        this.queryTimeout = queryTimeout;
        this.maxInMemorySize = maxInMemorySize;
        this.fullTime = fullTime;
        this.examPeriod = examPeriod;
        this.extramural = extramural;
    }
}