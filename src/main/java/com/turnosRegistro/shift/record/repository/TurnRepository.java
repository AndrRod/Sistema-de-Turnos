package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.Turn;
import com.turnosRegistro.shift.record.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    @Query("SELECT t FROM Turn t WHERE company = :company")
    Page<Company> findCompaniesByUser(@Param("company") Company company, Pageable pageable);
    @Query("SELECT COUNT(t) FROM Turn t WHERE t.reserves AND t.id= :idTurn")
    Long countReserves(@Param("idTurn")Long idTurn);
}
