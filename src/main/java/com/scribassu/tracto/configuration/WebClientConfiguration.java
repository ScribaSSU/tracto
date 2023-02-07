package com.scribassu.tracto.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class WebClientConfiguration {

    @Value("${tracto.download-schedule.base-url}")
    private String fullTimeScheduleUrl;

    @Bean
    @Qualifier("sguFullTimeScheduleWebClient")
    public WebClient sguFullTimeScheduleWebClient() throws SSLException {
        final var sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        final var exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer.defaultCodecs().maxInMemorySize(20 * 1024 * 1024))
                .build();

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 600000)
                .secure( s -> s.sslContext(sslContext))
                .resolver(spec -> spec.queryTimeout(Duration.ofSeconds(60)))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(600000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(600000, TimeUnit.MILLISECONDS));
                });
        return WebClient.builder()
                .baseUrl(fullTimeScheduleUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(exchangeStrategies)
                .filter((clientRequest, nextFilter) -> {
                    log.info("Sending request " + clientRequest.method() + " " + clientRequest.url());
                    return nextFilter.exchange(clientRequest);
                })
                .build();
    }
}
