package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.WeekShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface WeekShiftRepository extends JpaRepository<WeekShift, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update week_shift set shift = :shift where department = :department")
    void updateByDepartment(int shift, String department);

    Optional<WeekShift> findByDepartment(@Param("department") String department);
}
