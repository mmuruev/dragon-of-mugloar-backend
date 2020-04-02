package com.dragonofmugloar.backend.webclient;


import com.google.common.net.HttpHeaders;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HttpClientToMugloarFactory {

    private final int HTTP_REQUEST_TIMEOUT_MILLISECONDS;

    private final String BASE_API_URL;

    public HttpClientToMugloarFactory(
        @Value("${api.base.url}") String baseUrlApi,
        @Value("${api.request.timeout.milliseconds:1000}") int httpRequestTimeoutMilliseconds
    ) {
        BASE_API_URL = baseUrlApi;
        HTTP_REQUEST_TIMEOUT_MILLISECONDS = httpRequestTimeoutMilliseconds;
    }


    @Bean
    public WebClient getWebClientBean() {
        return initClient();
    }

    private WebClient initClient() {

        log.info("Init WebClient for API: {} with timeout: {}", BASE_API_URL, HTTP_REQUEST_TIMEOUT_MILLISECONDS);

        final TcpClient tcpClient = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, HTTP_REQUEST_TIMEOUT_MILLISECONDS)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(HTTP_REQUEST_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(HTTP_REQUEST_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS));
            });

        return WebClient
            .builder()
            .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .baseUrl(BASE_API_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", BASE_API_URL))
            .build();
    }
}
