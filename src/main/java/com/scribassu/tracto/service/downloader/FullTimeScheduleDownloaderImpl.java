package com.scribassu.tracto.service.downloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FullTimeScheduleDownloaderImpl implements ScheduleDownloader {

    @Qualifier("sguFullTimeScheduleWebClient")
    private final WebClient sguFullTimeScheduleWebClient;

    @Value("${tracto.download-schedule.full-time-url}")
    private String fullTimeScheduleUrl;

    @Autowired
    public FullTimeScheduleDownloaderImpl(WebClient sguFullTimeScheduleWebClient) {
        this.sguFullTimeScheduleWebClient = sguFullTimeScheduleWebClient;
    }

    /**
     * @return Downloaded schedule
     */
    public String downloadSchedule(String department) {
        return sguFullTimeScheduleWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(fullTimeScheduleUrl)
                        .queryParam("dep", department)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
