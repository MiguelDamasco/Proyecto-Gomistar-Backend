package com.gomistar.proyecto_gomistar.configuration.s3;

import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConfiguration {
    
    @Bean
    RestTemplate RestTemplate(RestTemplateBuilder rt) {
        return rt.build();
    }
}
