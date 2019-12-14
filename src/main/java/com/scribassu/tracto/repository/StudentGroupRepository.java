package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.EducationForm;
import com.scribassu.tracto.domain.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    @Query("select sg from StudentGroup sg where sg.groupNumber = :groupNumber and sg.educationForm = :educationForm and sg.department = :department")
    StudentGroup findByNumberAndEducationFormAndDepartment(@Param("groupNumber") String groupNumber,
                                                           @Param("educationForm") EducationForm educationForm,
                                                           @Param("department") Department department);
}
