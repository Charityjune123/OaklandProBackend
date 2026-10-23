package com.example.commercialcleaningbackend.controller;

import com.example.commercialcleaningbackend.model.Contract;
import com.example.commercialcleaningbackend.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "*")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @GetMapping("/customer/{customerId}")
    public List<Contract> getContractsByCustomerId(@PathVariable Long customerId) {
        return contractRepository.findByCustomerId(customerId);
    }

    @PostMapping
    public Contract createContract(@RequestBody Contract contract) {
        return contractRepository.save(contract);
    }

    // Changes only the status and who cleans the site. Fields left out are untouched.
    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, @RequestBody Contract updated) {
        return contractRepository.findById(id).map(c -> {
            if (updated.getStatus() != null) {
                c.setStatus(updated.getStatus());
            }
            if (updated.getAssignedTo() != null) {
                c.setAssignedTo(updated.getAssignedTo());
            }
            return ResponseEntity.ok(contractRepository.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    // "Mark done": records today as the last clean and moves the next clean date forward.
    @PutMapping("/{id}/complete")
    public ResponseEntity<Contract> completeClean(@PathVariable Long id) {
        return contractRepository.findById(id).map(c -> {
            LocalDate today = LocalDate.now();
            LocalDate next = c.getNextCleanDate() != null ? c.getNextCleanDate() : today;
            next = addInterval(next, c.getFrequency());
            while (!next.isAfter(today)) {
                next = addInterval(next, c.getFrequency());
            }
            c.setLastCleanDate(today);
            c.setNextCleanDate(next);
            return ResponseEntity.ok(contractRepository.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    private LocalDate addInterval(LocalDate from, String frequency) {
        if (frequency == null) {
            return from.plusWeeks(1);
        }
        return switch (frequency) {
            case "DAILY" -> from.plusDays(1);
            case "BI-WEEKLY" -> from.plusWeeks(2);
            case "MONTHLY" -> from.plusMonths(1);
            default -> from.plusWeeks(1);
        };
    }
}