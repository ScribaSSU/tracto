package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    @Query("select d from Day d where d.dayNumber = :dayNumber")
    Day findByDayNumber(@Param("dayNumber") int dayNumber);
}
