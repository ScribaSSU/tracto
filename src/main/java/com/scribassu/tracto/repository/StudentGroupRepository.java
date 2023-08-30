package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.Department;
import com.scribassu.tracto.entity.schedule.EducationForm;
import com.scribassu.tracto.entity.schedule.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    @Query("select sg from StudentGroup sg where sg.groupNumberRus = :groupNumber and" +
            " sg.educationForm = :educationForm and sg.department = :department")
    StudentGroup findByNumberAndEducationFormAndDepartment(@Param("groupNumber") String groupNumber,
                                                           @Param("educationForm") EducationForm educationForm,
                                                           @Param("department") Department department);

    @Query("select sg from StudentGroup sg left join sg.department d where d.url = :url and" +
            " sg.educationForm = :educationForm and sg.groupNumberRus like concat(:course, '%')")
    List<StudentGroup> findByDepartmentUrlAndEducationFormAndCourse(@Param("url") String departmentUrl,
                                                                    @Param("educationForm") EducationForm educationForm,
                                                                    @Param("course") String course);

    @Query("select sg from StudentGroup sg left join sg.department d where d.url = :url and" +
            " sg.educationForm = :educationForm")
    List<StudentGroup> findByDepartmentUrlAndEducationForm(@Param("url") String departmentUrl,
                                                           @Param("educationForm") EducationForm educationForm);

    @Query("select sg from StudentGroup sg left join sg.department d where d.url = :url")
    List<StudentGroup> findByDepartmentUrl(@Param("url") String departmentUrl);

    void deleteByEducationForm(EducationForm educationForm);
}
