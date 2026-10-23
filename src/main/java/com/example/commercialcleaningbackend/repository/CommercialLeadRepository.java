package com.example.commercialcleaningbackend.repository; // Update to match your package name

import com.example.commercialcleaningbackend.model.CommercialLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialLeadRepository extends JpaRepository<CommercialLead, Long> {
    // Spring Data JPA automatically provides CRUD methods like save(), findAll(), findById()
}