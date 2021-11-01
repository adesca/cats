package com.example.catdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    @Bean
    public WebClient getWebClient() {
        return WebClient.create("https://api.thecatapi.com");
    }
}
