package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
