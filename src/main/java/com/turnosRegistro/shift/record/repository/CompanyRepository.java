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
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE userCompany = :userCompany")
    Page<Company> findCompaniesByUser(@Param("userCompany") User userCompany, Pageable pageable);
    @Query("SELECT c.turn FROM Company c WHERE name = :name")
    Page<Turn> findTurnsByCompanyName(@Param("name") String name, Pageable pageable);
}
