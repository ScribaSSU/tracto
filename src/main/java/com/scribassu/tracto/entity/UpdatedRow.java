package com.scribassu.tracto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "updated_row")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class UpdatedRow {

    @Id
    @GeneratedValue
    private Long id;

    private Long oldId;

    private Long updatedId;

    private String updatedTable;

    @CreatedDate
    private OffsetDateTime lastUpdateTime;

    public UpdatedRow(Long oldId, Long updatedId, String updatedTable) {
        this.oldId = oldId;
        this.updatedId = updatedId;
        this.updatedTable = updatedTable;
    }
}