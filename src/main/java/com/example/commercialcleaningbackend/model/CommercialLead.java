package com.example.commercialcleaningbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "commercial_leads")
public class CommercialLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("facilityType")
    @Column(name = "facility_type", nullable = false, length = 100)
    private String facilityType;

    @JsonProperty("squareFootage")
    @Column(name = "square_footage", nullable = false)
    private Integer squareFootage;

    @JsonProperty("cleaningFrequency")
    @Column(name = "cleaning_frequency", nullable = false, length = 50)
    private String cleaningFrequency;

    @JsonProperty("estimatedPriceMin")
    @Column(name = "estimated_price_min", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedPriceMin;

    @JsonProperty("estimatedPriceMax")
    @Column(name = "estimated_price_max", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedPriceMax;

    @JsonProperty("requestedStartDate")
    @Column(name = "requested_start_date")
    private LocalDate requestedStartDate;

    @JsonProperty("walkthroughStatus")
    @Column(name = "walkthrough_status", length = 50)
    private String walkthroughStatus = "PENDING_CONFIRMATION";

    @JsonProperty("companyName")
    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @JsonProperty("contactName")
    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    // Constructors
    public CommercialLead() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFacilityType() { return facilityType; }
    public void setFacilityType(String facilityType) { this.facilityType = facilityType; }

    public Integer getSquareFootage() { return squareFootage; }
    public void setSquareFootage(Integer squareFootage) { this.squareFootage = squareFootage; }

    public String getCleaningFrequency() { return cleaningFrequency; }
    public void setCleaningFrequency(String cleaningFrequency) { this.cleaningFrequency = cleaningFrequency; }

    public BigDecimal getEstimatedPriceMin() { return estimatedPriceMin; }
    public void setEstimatedPriceMin(BigDecimal estimatedPriceMin) { this.estimatedPriceMin = estimatedPriceMin; }

    public BigDecimal getEstimatedPriceMax() { return estimatedPriceMax; }
    public void setEstimatedPriceMax(BigDecimal estimatedPriceMax) { this.estimatedPriceMax = estimatedPriceMax; }

    public LocalDate getRequestedStartDate() { return requestedStartDate; }
    public void setRequestedStartDate(LocalDate requestedStartDate) { this.requestedStartDate = requestedStartDate; }

    public String getWalkthroughStatus() { return walkthroughStatus; }
    public void setWalkthroughStatus(String walkthroughStatus) { this.walkthroughStatus = walkthroughStatus; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}