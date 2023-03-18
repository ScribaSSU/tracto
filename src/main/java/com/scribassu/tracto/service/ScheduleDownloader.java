package com.scribassu.tracto.service;

import com.scribassu.tracto.properties.DownloadScheduleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScheduleDownloader {

    private final WebClient fullTimeScheduleWebClient;
    private final WebClient examPeriodScheduleWebClient;
    private final WebClient extramuralScheduleWebClient;
    private final DownloadScheduleProperties downloadScheduleProperties;

    private static final String DEPARTMENT_PARAM = "dep";

    @Autowired
    public ScheduleDownloader(WebClient fullTimeScheduleWebClient,
                              WebClient examPeriodScheduleWebClient,
                              WebClient extramuralScheduleWebClient,
                              DownloadScheduleProperties downloadScheduleProperties) {
        this.fullTimeScheduleWebClient = fullTimeScheduleWebClient;
        this.examPeriodScheduleWebClient = examPeriodScheduleWebClient;
        this.extramuralScheduleWebClient = extramuralScheduleWebClient;
        this.downloadScheduleProperties = downloadScheduleProperties;
    }

    /**
     * @return Downloaded full-time schedule
     */
    public String downloadFullTimeSchedule(String department) {
        return fullTimeScheduleWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(downloadScheduleProperties.getFullTime().getPath())
                        .queryParam(DEPARTMENT_PARAM, department)
                        .build())
                .headers(httpHeaders -> httpHeaders.setBasicAuth(downloadScheduleProperties.getFullTime().getAuthHeader()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String downloadExamPeriodSchedule(String department,
                                             String educationForm,
                                             String groupNumber) {
        return examPeriodScheduleWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(downloadScheduleProperties.getExamPeriod().getPath())
                        .build(department, educationForm, groupNumber))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String downloadExtramuralSchedule(String department,
                                             String educationForm,
                                             String groupNumber) {
        return extramuralScheduleWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(downloadScheduleProperties.getExtramural().getPath())
                        .build(department, educationForm, groupNumber))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
