package com.example.gamgyulman.domain.openApi.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Component
@Slf4j
public class OpenApiWebClientImpl implements OpenApiWebClient {

    @Value("${open-api.uri.base.english}")
    private String baseUrl;

    @Override
    public WebClient getEnglishTravelWebClient() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(20000));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter((request, next) -> {
                    log.info("Web Client Request: "+ request.url());
                    return next.exchange(request);
                })
                .build();
    }
}
