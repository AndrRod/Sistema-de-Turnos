package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.enums.Day;
import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.Turn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    @Query("SELECT t FROM Turn t WHERE company = :company")
    Page<Company> findCompaniesByUser(@Param("company") Company company, Pageable pageable);

    @Query("SELECT t FROM Turn t WHERE t.dayTurn = :dayTurn AND company.id = :idCompany")
    Page<Turn> findReserveByDayName(@Param("idCompany") Long idCompany, @Param("dayTurn") Day dayTurn, Pageable pageable);
}
