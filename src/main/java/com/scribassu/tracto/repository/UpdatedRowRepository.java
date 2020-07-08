package com.scribassu.tracto.repository;

import com.scribassu.tracto.entity.UpdatedRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdatedRowRepository extends JpaRepository<UpdatedRow, Long> {
}
