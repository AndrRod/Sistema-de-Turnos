package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
}
