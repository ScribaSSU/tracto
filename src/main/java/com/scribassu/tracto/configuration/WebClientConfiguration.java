package com.scribassu.tracto.configuration;

import com.scribassu.tracto.properties.DownloadScheduleProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.scribassu.tracto.util.WebClientUtil.INSECURE_SSL_CONTEXT;

@Slf4j
@Configuration
public class WebClientConfiguration {

    private final DownloadScheduleProperties downloadScheduleProperties;

    @Autowired
    public WebClientConfiguration(DownloadScheduleProperties downloadScheduleProperties) {
        this.downloadScheduleProperties = downloadScheduleProperties;
    }

    @Bean
    public WebClient fullTimeScheduleWebClient() {
        HttpClient httpClient = createHttpClient(
                downloadScheduleProperties.getFullTime().getConnectionTimeout(),
                downloadScheduleProperties.getQueryTimeout(),
                downloadScheduleProperties.getFullTime().getReadTimeout(),
                downloadScheduleProperties.getFullTime().getWriteTimeout());

        return createWebClient(
                downloadScheduleProperties.getBaseUrl(),
                httpClient,
                downloadScheduleProperties.getMaxInMemorySize());
    }

    @Bean
    public WebClient examPeriodScheduleWebClient() {
        HttpClient httpClient = createHttpClient(
                downloadScheduleProperties.getExamPeriod().getConnectionTimeout(),
                downloadScheduleProperties.getQueryTimeout(),
                downloadScheduleProperties.getExamPeriod().getReadTimeout(),
                downloadScheduleProperties.getExamPeriod().getWriteTimeout());

        return createWebClient(
                downloadScheduleProperties.getBaseUrl(),
                httpClient,
                downloadScheduleProperties.getMaxInMemorySize());
    }

    @Bean
    public WebClient extramuralScheduleWebClient() {
        HttpClient httpClient = createHttpClient(
                downloadScheduleProperties.getExtramural().getConnectionTimeout(),
                downloadScheduleProperties.getQueryTimeout(),
                downloadScheduleProperties.getExtramural().getReadTimeout(),
                downloadScheduleProperties.getExtramural().getWriteTimeout());

        return createWebClient(
                downloadScheduleProperties.getBaseUrl(),
                httpClient,
                downloadScheduleProperties.getMaxInMemorySize());
    }

    private HttpClient createHttpClient(int connectionTimeout,
                                        int queryTimeout,
                                        int readTimeout,
                                        int writeTimeout) {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .secure( s -> s.sslContext(INSECURE_SSL_CONTEXT))
                .resolver(spec -> spec.queryTimeout(Duration.ofMillis(queryTimeout)))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS));
                });
    }

    private WebClient createWebClient(String baseUrl,
                                      HttpClient httpClient,
                                      int maxInMemorySize) {
        final var exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer.defaultCodecs().maxInMemorySize(maxInMemorySize))
                .build();
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(exchangeStrategies)
                .filter(this::loggingFilter)
                .build();
    }

    private Mono<ClientResponse> loggingFilter(ClientRequest clientRequest,
                                               ExchangeFunction nextFilter) {
            log.info("Send request " + clientRequest.method() + " " + clientRequest.url());
            return nextFilter.exchange(clientRequest);
    }
}
