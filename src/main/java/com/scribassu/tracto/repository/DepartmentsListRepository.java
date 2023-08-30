package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentsListRepository extends JpaRepository<Department, Long> {
    @Query("select ds from Department ds")
    List<Department> getDepartmentsList();
}
