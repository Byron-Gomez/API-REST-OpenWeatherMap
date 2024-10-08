package com.byronxgomez.apiopenweathermapspringboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.byronxgomez")
                .packagesToScan("com.byronxgomez.apiopenweathermapspringboot.controller")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API OpenWeatherMap")
                        .version("1.0")
                        .description("API para obtener datos meteorológicos")
                        .contact(new Contact()
                                .name("Byron Gomez")
                                .url("http://byrongomez.com")
                                .email("byron@example.com")));
    }
}
