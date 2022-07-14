package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
