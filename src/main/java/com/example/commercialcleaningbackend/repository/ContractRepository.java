package com.example.commercialcleaningbackend.repository;

import com.example.commercialcleaningbackend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByCustomerId(Long customerId);
}
