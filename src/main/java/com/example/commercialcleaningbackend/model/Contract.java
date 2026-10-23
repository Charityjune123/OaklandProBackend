package com.example.commercialcleaningbackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "facility_type", length = 100)
    private String facilityType;

    @Column(name = "square_footage")
    private Integer squareFootage;

    @Column(length = 50)
    private String frequency;

    @Column(name = "agreed_amount", precision = 10, scale = 2)
    private BigDecimal agreedAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "next_clean_date")
    private LocalDate nextCleanDate;

    @Column(length = 30)
    private String status = "ACTIVE";

    @Column(name = "assigned_to", length = 30)
    private String assignedTo = "OWNER";

    @Column(name = "last_clean_date")
    private LocalDate lastCleanDate;

    public Contract() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public Integer getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getAgreedAmount() {
        return agreedAmount;
    }

    public void setAgreedAmount(BigDecimal agreedAmount) {
        this.agreedAmount = agreedAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getNextCleanDate() {
        return nextCleanDate;
    }

    public void setNextCleanDate(LocalDate nextCleanDate) {
        this.nextCleanDate = nextCleanDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDate getLastCleanDate() {
        return lastCleanDate;
    }

    public void setLastCleanDate(LocalDate lastCleanDate) {
        this.lastCleanDate = lastCleanDate;
    }
}
