package com.management.lead.leadMangement.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class LeadTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String status;

    @OneToOne
    @JoinColumn(name = "lead_id") // This should match the primary key column in the Lead entity
    private LeadCapture lead;

    @Temporal(TemporalType.DATE)
    private Date actualClosingDate = null;

}
