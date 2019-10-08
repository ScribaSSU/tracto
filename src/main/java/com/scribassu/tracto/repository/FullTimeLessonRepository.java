package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.FullTimeLessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullTimeLessonRepository extends JpaRepository<FullTimeLessonEntity, Long> {

    @Query("from FullTimeLessonEntity where groupType = :groupType and group = :group")
    List<FullTimeLessonEntity> findAllForGroup(@Param("groupType") String groupType,
                                               @Param("group") String group);
}
