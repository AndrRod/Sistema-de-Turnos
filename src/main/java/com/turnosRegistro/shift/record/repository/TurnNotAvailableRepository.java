package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.TurnNotAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TurnNotAvailableRepository extends JpaRepository<TurnNotAvailable, Long> {
    @Modifying
    @Query("DELETE FROM TurnNotAvailable t WHERE dateTurn<:date")
    void deleteTurnNotAvailableExpired(@Param("date") LocalDate date);
}
