package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.schedule.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("select t from Teacher t where t.id = :id")
    Teacher getById(@Param("id") Long id);

    @Query("select t from Teacher t where t.surname = :surname and t.name = :name and t.patronymic = :patronymic")
    List<Teacher> findBySurnameAndNameAndPatronymic(@Param("surname") String surname,
                                                    @Param("name") String name,
                                                    @Param("patronymic") String patronymic);

    @Query("select t from Teacher t where " +
            "t.surname like concat(:surname, '%') " +
            "or t.name like concat(:name, '%') " +
            "or t.patronymic like concat(:patronymic, '%')")
    List<Teacher> findByAnyPartOfName(@Param("surname") String surname,
                                      @Param("name") String name,
                                      @Param("patronymic") String patronymic);

    @Query("select t from Teacher t where " +
            "concat(t.surname, ' ', t.name, ' ', t.patronymic) like concat(:fullName, '%')")
    List<Teacher> findByFullNameLike(@Param("fullName") String fullName);

    @Query("select t from Teacher t where t.surname = :surname")
    List<Teacher> findBySurname(@Param("surname") String surname);
}
