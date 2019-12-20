package com.scribassu.tracto.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ScheduleDownloaderImpl implements ScheduleDownloader {

    @Value("${tracto.download-schedule.url}")
    private String url;

    @Value("${tracto.download-schedule.auth-header}")
    private String authHeader;

    private final String authorization = "Authorization";

    private HttpClient httpClient = HttpClientBuilder.create().build();
    private HttpResponse httpResponse;
    private HttpGet httpGet;

    /**
     * @param departmentUrl department url
     * @return downloaded schedule
     */
    @Override
    public String downloadSchedule(String departmentUrl) {
        String scheduleUrl = url + departmentUrl;

        try {
            httpGet = new HttpGet(scheduleUrl);
            httpGet.addHeader(authorization, authHeader);
            httpResponse = httpClient.execute(httpGet);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent())
            );

            StringBuilder stringBuilder = new StringBuilder();
            String outLine;

            while((outLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(outLine);
            }
            return stringBuilder.toString();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
