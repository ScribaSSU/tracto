package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.ScheduleParserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleParserStatusRepository extends JpaRepository<ScheduleParserStatus, Long> {
}
