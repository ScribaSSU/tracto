package com.scribassu.tracto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "schedule_parser_status")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class ScheduleParserStatus {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private OffsetDateTime lastUpdateTime;

    @Column(length = 50)
    private String status;

    @Column(length = 50)
    private String schedule;

    public ScheduleParserStatus(String status, String schedule) {
        this.status = status;
        this.schedule = schedule;
    }
}
