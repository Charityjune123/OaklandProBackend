package com.example.commercialcleaningbackend.repository; // Update to match your package name

import com.example.commercialcleaningbackend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    List<Campaign> findAllByOrderByCreatedAtDesc();
}