package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.FullTimeLesson;
import com.scribassu.tracto.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FullTimeLessonRepository extends JpaRepository<FullTimeLesson, Long> {

    @Query("select ftl from FullTimeLesson ftl where ftl.groupNumber = :groupNumber")
    List<FullTimeLesson> findAllForGroup(@Param("groupNumber") String groupNumber);

    Optional<FullTimeLesson> findByGroupNumberAndLessonTimeAndWeekDay(String groupNumber, Time time, Day day);
}
