package com.example.gamgyulman.global.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

public class CorsConfig {

    public static CorsConfigurationSource cors() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> allowedURL = new ArrayList<>();
        allowedURL.add("http://localhost:8080");
        allowedURL.add("http://localhost:5173");
        allowedURL.add("http://localhost:3000");

        List<String> allowedMethod = new ArrayList<>();
        allowedMethod.add("GET");
        allowedMethod.add("POST");
        allowedMethod.add("DELETE");
        allowedMethod.add("PATCH");

        corsConfiguration.setAllowedOrigins(allowedURL);
        corsConfiguration.setAllowedMethods(allowedMethod);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", corsConfiguration);
        return cors;
    }
}
