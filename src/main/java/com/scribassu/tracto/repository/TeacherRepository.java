package com.scribassu.tracto.repository;

import com.scribassu.tracto.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("select t from Teacher t where t.surname = :surname and t.name = :name and t.patronymic = :patronymic")
    List<Teacher> findBySurnameAndNameAndPatronymic(@Param("surname") String surname,
                                                    @Param("name") String name,
                                                    @Param("patronymic") String patronymic);
}
