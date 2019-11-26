package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.LessonTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonTimeRepository extends JpaRepository<LessonTime, Long> {
    Optional<LessonTime> findByLessonNumber(int lessonNumber);
}
