package com.scribassu.tracto.configuration;

import com.scribassu.tracto.properties.ProjectProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfiguration {

    private final ProjectProperties projectProperties;

    @Autowired
    public OpenApiConfiguration(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(apiInfo());
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
