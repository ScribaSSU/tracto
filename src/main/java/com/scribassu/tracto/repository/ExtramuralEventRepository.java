package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.ExamPeriodMonth;
import com.scribassu.tracto.domain.ExtramuralEvent;
import com.scribassu.tracto.domain.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExtramuralEventRepository extends JpaRepository<ExtramuralEvent, Long> {

    @Query("select e from ExtramuralEvent e where e.studentGroup = :studentGroup")
    List<ExtramuralEvent> findByStudentGroup(@Param("studentGroup") StudentGroup studentGroup);

    @Query("select e from ExtramuralEvent e where e.teacher = :teacher")
    List<ExtramuralEvent> findByTeacher(@Param("teacher") String teacher);

    @Modifying
    @Transactional
    @Query("delete from ExtramuralEvent e where e.studentGroup = :studentGroup")
    void deleteByStudentGroup(@Param("studentGroup") StudentGroup group);

    @Query("select e from ExtramuralEvent e where e.studentGroup = :studentGroup and e.month = :month and e.day = :day")
    List<ExtramuralEvent> findByStudentGroupAndMonthAndDay(@Param("studentGroup") StudentGroup studentGroup,
                                                           @Param("month") ExamPeriodMonth month,
                                                           @Param("day") Integer day);
}
