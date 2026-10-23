package com.example.commercialcleaningbackend.controller;

import com.example.commercialcleaningbackend.model.CommercialLead;
import com.example.commercialcleaningbackend.repository.CommercialLeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "http://localhost:4200")
public class CommercialLeadController {

    @Autowired
    private CommercialLeadRepository leadRepository;

    // Get all leads
    @GetMapping
    public List<CommercialLead> getAllLeads() {
        return leadRepository.findAll();
    }

    // Create a new lead
    @PostMapping
    public CommercialLead createLead(@RequestBody CommercialLead lead) {
        return leadRepository.save(lead);
    }

    // Delete a lead
    @DeleteMapping("/{id}")
    public void deleteLead(@PathVariable Long id) {
        leadRepository.deleteById(id);
    }
}
