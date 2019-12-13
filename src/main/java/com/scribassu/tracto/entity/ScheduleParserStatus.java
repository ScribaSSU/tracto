package com.scribassu.tracto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "schedule_parser_status")
@Data
@NoArgsConstructor
public class ScheduleParserStatus {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private OffsetDateTime lastUpdateTime;
    private String status;
    private String schedule;

    public ScheduleParserStatus(String status, String schedule) {
        this.status = status;
        this.schedule = schedule;
    }
}
