package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.TurnNotAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnNotAvailableRepository extends JpaRepository<TurnNotAvailable, Long> {
}
