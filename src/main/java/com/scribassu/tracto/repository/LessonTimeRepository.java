package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.LessonTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonTimeRepository extends JpaRepository<LessonTime, Long> {
    LessonTime findByLessonNumber(int lessonNumber);
}
