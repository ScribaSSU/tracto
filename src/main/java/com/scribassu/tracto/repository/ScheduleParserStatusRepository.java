package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.ScheduleParserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleParserStatusRepository extends JpaRepository<ScheduleParserResult, Long> {
}
