package com.management.lead.leadMangement.entity;

import java.util.Date;

import com.management.lead.leadMangement.enumConstants.ActivityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "leadsActivity")
@Data
public class LeadActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Activity type is required")
    @Size(max = 255, message = "Activity type must be less than 255 characters")
    private String activityType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @NotBlank(message = "Summary is required")
    @Size(max = 255, message = "Summary must be less than 255 characters")
    private String summary;

    @NotBlank(message = "Detail is required")
    private String detail;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "lead_id")
    private LeadCapture lead;
}
