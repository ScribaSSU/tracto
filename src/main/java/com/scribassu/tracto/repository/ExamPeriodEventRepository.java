package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.ExamPeriodEvent;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExamPeriodEventRepository extends JpaRepository<ExamPeriodEvent, Long> {

    @Query("select epe from ExamPeriodEvent epe where epe.studentGroup = :studentGroup")
    List<ExamPeriodEvent> findByStudentGroup(@Param("studentGroup") StudentGroup studentGroup);

    @Modifying
    @Transactional
    @Query("delete from ExamPeriodEvent epe where epe.studentGroup = :studentGroup")
    void deleteByStudentGroup(@Param("studentGroup") StudentGroup studentGroup);

    @Query("select epe from ExamPeriodEvent epe where epe.teacher = :teacher")
    List<ExamPeriodEvent> findByTeacher(@Param("teacher") Teacher teacher);
}

