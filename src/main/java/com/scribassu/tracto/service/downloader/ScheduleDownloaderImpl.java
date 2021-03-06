package com.scribassu.tracto.service.downloader;

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

    private final String authorization = "Authorization";

    @Value("${tracto.download-schedule.url}")
    private String fullTimeScheduleUrl;

    @Value("${tracto.download-schedule.auth-header}")
    private String authHeader;

    private HttpClient httpClient = HttpClientBuilder.create().build();

    private HttpResponse httpResponse;

    private HttpGet httpGet;

    /**
     * @return downloaded schedule
     */
    @Override
    public String downloadSchedule(String url) {

        try {
            httpGet = new HttpGet(url);

            if(url.startsWith(fullTimeScheduleUrl)) {
                httpGet.addHeader(authorization, authHeader);
            }
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
        finally {
            httpGet.abort();
        }

        return "";
    }
}
