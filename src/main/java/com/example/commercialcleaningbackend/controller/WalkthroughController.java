package com.example.commercialcleaningbackend.controller;

import com.example.commercialcleaningbackend.model.CommercialLead;
import com.example.commercialcleaningbackend.model.Walkthrough;
import com.example.commercialcleaningbackend.repository.CommercialLeadRepository;
import com.example.commercialcleaningbackend.repository.WalkthroughRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/walkthroughs")
@CrossOrigin(origins = "http://localhost:4200")
public class WalkthroughController {

    @Autowired
    private WalkthroughRepository walkthroughRepository;

    @Autowired
    private CommercialLeadRepository leadRepository;

    @GetMapping
    public List<Map<String, Object>> getAllWalkthroughs() {

        List<Walkthrough> walkthroughs = walkthroughRepository.findAll();

        return walkthroughs.stream().map(walkthrough -> {

            Map<String, Object> result = new HashMap<>();

            result.put("id", walkthrough.getId());
            result.put("leadId", walkthrough.getLeadId());
            result.put("scheduledDate", walkthrough.getScheduledDate());
            result.put("status", walkthrough.getStatus());
            result.put("notes", walkthrough.getNotes());

            CommercialLead lead = leadRepository
                    .findById(walkthrough.getLeadId())
                    .orElse(null);

            if (lead != null) {
                Map<String, Object> leadData = new HashMap<>();

                leadData.put("companyName", lead.getCompanyName());
                leadData.put("contactName", lead.getContactName());
                leadData.put("email", lead.getEmail());
                leadData.put("phone", lead.getPhone());
                leadData.put("facilityType", lead.getFacilityType());
                leadData.put("squareFootage", lead.getSquareFootage());
                leadData.put("cleaningFrequency", lead.getCleaningFrequency());
                leadData.put("estimatedPriceMin", lead.getEstimatedPriceMin());
                leadData.put("estimatedPriceMax", lead.getEstimatedPriceMax());
                leadData.put("requestedStartDate", lead.getRequestedStartDate());

                result.put("lead", leadData);
            }

            return result;

        }).collect(Collectors.toList());
    }

    @PostMapping
    public Walkthrough createWalkthrough(@RequestBody Walkthrough walkthrough) {
        return walkthroughRepository.save(walkthrough);
    }

    // Updates status, notes, or the scheduled time. Fields left out are untouched.
    @PutMapping("/{id}")
    public ResponseEntity<Walkthrough> updateWalkthrough(@PathVariable Long id, @RequestBody Walkthrough updated) {
        return walkthroughRepository.findById(id).map(w -> {
            if (updated.getStatus() != null) {
                w.setStatus(updated.getStatus());
            }
            if (updated.getNotes() != null) {
                w.setNotes(updated.getNotes());
            }
            if (updated.getScheduledDate() != null) {
                w.setScheduledDate(updated.getScheduledDate());
            }
            return ResponseEntity.ok(walkthroughRepository.save(w));
        }).orElse(ResponseEntity.notFound().build());
    }
}