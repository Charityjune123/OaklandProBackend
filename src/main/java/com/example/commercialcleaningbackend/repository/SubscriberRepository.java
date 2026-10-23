package com.example.commercialcleaningbackend.repository;

import com.example.commercialcleaningbackend.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    boolean existsByEmail(String email);
    Optional<Subscriber> findByEmail(String email);
    List<Subscriber> findByStatus(String status);
    List<Subscriber> findAllByOrderByCreatedAtDesc();
}