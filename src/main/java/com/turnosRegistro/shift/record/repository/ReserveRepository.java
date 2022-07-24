package com.turnosRegistro.shift.record.repository;
import com.turnosRegistro.shift.record.model.Reserve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT r FROM Reserve r WHERE company.id = :idCompany")
    Page<Reserve> findReserveByCompanyId(@Param("idCompany") Long idCompany, Pageable pageable);
    @Modifying
    @Query("DELETE FROM Reserve r WHERE dateTurn<:date")
    void deleteReserveExpired(@Param("date") LocalDate date);
}
