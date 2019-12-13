package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select d from Department d where d.URL = :url")
    Optional<Department> findByURL(@Param("url") String url);

}
