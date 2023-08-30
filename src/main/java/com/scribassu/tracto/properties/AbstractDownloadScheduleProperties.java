package com.scribassu.tracto.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConstructorBinding
public class AbstractDownloadScheduleProperties {
    private String path;
    private String timeUpdate;
    private int connectionTimeout;
    private int readTimeout;
    private int writeTimeout;
}
