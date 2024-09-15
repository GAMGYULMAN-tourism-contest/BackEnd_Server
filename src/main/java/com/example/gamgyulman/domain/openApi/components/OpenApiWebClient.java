package com.example.gamgyulman.domain.openApi.components;

import org.springframework.web.reactive.function.client.WebClient;

public interface OpenApiWebClient {

    WebClient getEnglishTravelWebClient();
}
