package com.management.lead.leadmangement.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.management.lead.leadmangement.enumconstants.LeadPriority;
import com.management.lead.leadmangement.enumconstants.LeadStage;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "leads")
@Data
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String quote;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal budget;

    @NotBlank
    private String probability;

    @Enumerated(EnumType.STRING)
    private LeadPriority priority;

    @Enumerated(EnumType.STRING)
    private LeadStage stage;

    @NotBlank
    private String source;

    @NotBlank
    private String tag;

    @ManyToOne
    private User assignedUser;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date expectedClosingDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lead_contact_id")
    private LeadContactInfo leadContact;
}
