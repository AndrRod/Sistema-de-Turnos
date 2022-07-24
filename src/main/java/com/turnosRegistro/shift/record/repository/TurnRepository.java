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

import java.util.Collection;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    @Query("SELECT t FROM Turn t WHERE company = :company")
    Page<Company> findCompaniesByUser(@Param("company") Company company, Pageable pageable);

    @Query("SELECT t FROM Turn t WHERE t.dayTurn = :dayTurn AND company.id = :idCompany ORDER BY t.startTurn")
    Page<Turn> findReserveByDayName(@Param("idCompany") Long idCompany, @Param("dayTurn") Day dayTurn, Pageable pageable);

    @Query("SELECT DISTINCT t.dayTurn FROM Turn t WHERE company.id = :idCompany ORDER BY t.dayTurn")
    Collection<Day> findDaysAvailableByIdCompany(@Param("idCompany") Long idCompany);
    @Query("SELECT t FROM Turn t WHERE company.id = :idCompany ORDER BY t.dayTurn, t.startTurn")
    Collection<Turn> findTurnByIdCompanyAndOrder(@Param("idCompany") Long idCompany);

    @Query("SELECT t FROM Turn t WHERE company.id = :idCompany ORDER BY t.dayTurn, t.startTurn")
    Page<Turn> findTurnPageByIdCompanyAndOrder(@Param("idCompany") Long idCompany, Pageable pageable);


}
