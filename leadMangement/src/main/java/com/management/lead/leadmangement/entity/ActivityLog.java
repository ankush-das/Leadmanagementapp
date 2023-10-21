package com.management.lead.leadmangement.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Table(name = "ActivityLog")
@Entity
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;

    @NotBlank
    private String details;

    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many-to-One relationship with Lead
    @ManyToOne
    @JoinColumn(name = "lead_id")
    private LeadCapture lead;
}
