package com.example.catdemo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@Configuration
public class ClockConfig {

    @Bean
    public Clock getActiveClock() {
        return Clock.systemUTC();
    }
}
