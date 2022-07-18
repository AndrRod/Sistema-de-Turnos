package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Company;
import com.turnosRegistro.shift.record.model.Reserve;
import com.turnosRegistro.shift.record.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT r FROM Reserve r WHERE company.name = :companyName")
    Page<Reserve> findReserveByCompany(@Param("companyName") String companyName, Pageable pageable);
    @Query("SELECT r FROM Reserve r WHERE user = :userEntity")
    Page<Reserve> findReserveByUser(@Param("userEntity") User user, Pageable pageable);
}
