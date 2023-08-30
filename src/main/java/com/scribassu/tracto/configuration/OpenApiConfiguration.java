package com.scribassu.tracto.configuration;

import com.scribassu.tracto.properties.ProjectProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@AllArgsConstructor
public class OpenApiConfiguration {

    private final ProjectProperties projectProperties;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(Collections.singletonList(new Server().url("/api")));
    }

    private Info apiInfo() {
        return new Info()
                .title("Tracto")
                .description("API for Saratov State University")
                .version(projectProperties.getVersion())
                .contact(
                        new Contact()
                                .name("Diana Mashkina, TG: @Dzeru")
                );
    }
}
