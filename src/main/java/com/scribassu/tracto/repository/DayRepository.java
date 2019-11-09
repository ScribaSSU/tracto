package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findByDayNumber(int dayNumber);
}
