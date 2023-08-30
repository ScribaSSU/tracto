package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select d from Department d where d.url = :url")
    Department findByUrl(@Param("url") String url);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update department set week_shift = :weekShift where url = :departmentUrl")
    void updateByDepartment(int weekShift, String departmentUrl);

}
