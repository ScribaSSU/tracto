package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
    Optional<Time> findByLessonNumber(int lessonNumber);
}
