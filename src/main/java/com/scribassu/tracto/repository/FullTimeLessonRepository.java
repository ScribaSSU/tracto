package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.FullTimeLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullTimeLessonRepository extends JpaRepository<FullTimeLesson, Long> {

    @Query("select ftl from FullTimeLesson ftl where ftl.groupNumber = :groupNumber")
    List<FullTimeLesson> findAllForGroup(@Param("groupNumber") String groupNumber);
}
