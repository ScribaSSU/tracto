package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.ExamPeriodMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamPeriodMonthRepository extends JpaRepository<ExamPeriodMonth, Integer> {

    @Query("select epm from ExamPeriodMonth epm where epm.number = :number")
    Optional<ExamPeriodMonth> findByNumber(@Param("number") Integer number);

    @Query("select epm from ExamPeriodMonth epm where epm.rusGenitive = :rusGenitive")
    List<ExamPeriodMonth> findByRusGenitive(@Param("rusGenitive") String rusGenitive);
}
