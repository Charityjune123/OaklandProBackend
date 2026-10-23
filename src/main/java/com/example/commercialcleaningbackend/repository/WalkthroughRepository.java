package com.example.commercialcleaningbackend.repository;

import com.example.commercialcleaningbackend.model.Walkthrough;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkthroughRepository extends JpaRepository<Walkthrough, Long> {
}