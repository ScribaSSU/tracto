package com.scribassu.tracto.service;

import com.scribassu.tracto.properties.DownloadScheduleProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@AllArgsConstructor
public class ScheduleDownloader {

    private final WebClient fullTimeScheduleWebClient;
    private final WebClient examPeriodScheduleWebClient;
    private final WebClient extramuralScheduleWebClient;
    private final DownloadScheduleProperties downloadScheduleProperties;

    private static final String DEPARTMENT_PARAM = "dep";

    /**
     * @return Downloaded full-time schedule
     */
    public String downloadFullTimeSchedule(String department) {
        log.info(String.format("Download full-time schedule for department %s", department));
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
        log.info(String.format("Download exam period schedule for department %s, education form %s, student group %s", department, educationForm, groupNumber));
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
        log.info(String.format("Download extramural schedule for department %s, education form %s, student group %s", department, educationForm, groupNumber));
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
