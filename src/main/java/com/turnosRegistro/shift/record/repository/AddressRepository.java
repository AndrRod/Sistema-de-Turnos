package com.turnosRegistro.shift.record.repository;

import com.turnosRegistro.shift.record.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
