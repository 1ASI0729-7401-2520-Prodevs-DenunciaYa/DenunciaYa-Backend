package com.denunciayabackend.authoritiesPanel.domain.model.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public ApplicationEventPublisher applicationEventPublisher(org.springframework.context.ApplicationContext context) {
        return context;
    }
}