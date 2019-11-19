package com.scribassu.tracto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class ScheduleParserStatusEntity {
    private Long id;

    @CreatedDate
    private OffsetDateTime lastUpdateTime;
    private String status;
    private String schedule;

    public ScheduleParserStatusEntity(String status, String schedule) {
        this.status = status;
        this.schedule = schedule;
    }
}
