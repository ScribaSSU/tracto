package com.scribassu.tracto.entity;

import com.scribassu.tracto.entity.schedule.ScheduleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Entity
@Table(name = "schedule_parser_status")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class ScheduleParserResult {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private OffsetDateTime lastUpdateTime;

    private String department;
    private String educationForm;
    private String studentGroup;

    private ScheduleParserStatus status;
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    private static final String NOT_STATED = "Не указано";

    public ScheduleParserResult(OffsetDateTime lastUpdateTime, String department, String educationForm, String studentGroup, ScheduleParserStatus status, ScheduleType scheduleType) {
        this.lastUpdateTime = lastUpdateTime;
        this.department = department;
        this.educationForm = educationForm;
        this.studentGroup = studentGroup;
        this.status = status;
        this.scheduleType = scheduleType;
    }

    public ScheduleParserResult(String department, String educationForm, String studentGroup, ScheduleParserStatus status, ScheduleType scheduleType) {
        this.department = department;
        this.educationForm = educationForm;
        this.studentGroup = studentGroup;
        this.status = status;
        this.scheduleType = scheduleType;
    }

    public ScheduleParserResult(Map<String, String> params) {
        this.department = params.getOrDefault("department", NOT_STATED);
        this.educationForm = params.getOrDefault("educationForm", NOT_STATED);
        this.studentGroup = params.getOrDefault("studentGroup", NOT_STATED);
        this.status = ScheduleParserStatus.valueOf(params.getOrDefault("status", ScheduleParserStatus.ERROR.name()));
        this.scheduleType = ScheduleType.valueOf(params.getOrDefault("scheduleType", ScheduleType.FULL_TIME.name()));
    }
}
