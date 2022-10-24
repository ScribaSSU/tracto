package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FullTimeLessonRepository extends JpaRepository<FullTimeLesson, Long> {

    @Query("select ftl from FullTimeLesson ftl where ftl.department = :department")
    List<FullTimeLesson> findByDepartment(@Param("department") Department department);

    @Query("select ftl from FullTimeLesson ftl where ftl.studentGroup = :studentGroup")
    List<FullTimeLesson> findByStudentGroup(@Param("studentGroup") StudentGroup studentGroup);

    @Query("select ftl from FullTimeLesson ftl where ftl.day = :day and ftl.studentGroup = :studentGroup")
    List<FullTimeLesson> findByDayAndStudentGroup(@Param("day") Day day,
                                                  @Param("studentGroup") StudentGroup studentGroup);

    @Query("select ftl from FullTimeLesson ftl where ftl.day = :day and ftl.lessonTime = :lessonTime and ftl.studentGroup = :studentGroup")
    List<FullTimeLesson> findByDayAndLessonTimeAndGroup(@Param("day") Day day,
                                                        @Param("lessonTime") LessonTime lessonTime,
                                                        @Param("studentGroup") StudentGroup studentGroup);

    @Query("select ftl from FullTimeLesson ftl where ftl.teacher = :teacher and ftl.day = :day")
    List<FullTimeLesson> findByTeacher(@Param("teacher") Teacher teacher,
                                       @Param("day") Day day);

    @Query("select ftl from FullTimeLesson ftl where " +
            "ftl.name = :name " +
            "and ftl.teacher = :teacher " +
            "and ftl.studentGroup = :studentGroup " +
            "and ftl.subGroup = :subGroup " +
            "and ftl.department = :department " +
            "and ftl.place = :place " +
            "and ftl.day = :day " +
            "and ftl.lessonTime = :lessonTime " +
            "and ftl.weekType = :weekType " +
            "and ftl.lessonType = :lessonType")
    List<FullTimeLesson> findEqual(@Param("name") String name,
                                   @Param("teacher") Teacher teacher,
                                   @Param("studentGroup") StudentGroup studentGroup,
                                   @Param("subGroup") String subGroup,
                                   @Param("department") Department department,
                                   @Param("place") String place,
                                   @Param("day") Day day,
                                   @Param("lessonTime") LessonTime lessonTime,
                                   @Param("weekType") WeekType weekType,
                                   @Param("lessonType") LessonType lessonType);

    @Transactional
    @Modifying
    @Query("delete from FullTimeLesson ftl where ftl.department in (select d from Department d where url = :department)")
    void deleteByDepartmentURL(@Param("department") String department);
}
