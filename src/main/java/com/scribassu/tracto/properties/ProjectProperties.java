package com.scribassu.tracto.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "project")
@Data
@NoArgsConstructor
public class ProjectProperties {

    private String version;

    public ProjectProperties(String version) {
        this.version = version;
    }
}
