package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.turnosRegistro.shift.record.model.Turn;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE userCompany = :userCompany")
    Page<Company> findCompaniesByUser(@Param("userCompany") User userCompany, Pageable pageable);
//    @Query("SELECT c.turn FROM Company c WHERE name = :companyName")
//    Page<Turn> findTurnsPageByCompanyName(@Param("companyName") String name, Pageable pageable);

//    @Query("SELECT c.turn FROM Company c WHERE c.id = :idCompany ORDER BY c.turn.dayTurn, c.turn.startTurn")
//    Page<Turn> findTurnsPageByIdCompany(@Param("idCompany") Long idCompany, Pageable pageable);
    @Query("SELECT c.turn FROM Company c WHERE userCompany = :userCompany")
    List<Turn> findAllTurnsByUser(@Param("userCompany") User userCompany);
}
