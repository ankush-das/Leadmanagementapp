package com.management.lead.leadMangement.dto;

import java.util.Date;

import com.management.lead.leadMangement.entity.User;
import com.management.lead.leadMangement.enumConstants.ActivityStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LeadActivityDTO {

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @NotBlank(message = "Activity type is required")
    @Size(max = 255, message = "Activity type must be less than 255 characters")
    private String activityType;

    @NotBlank(message = "Detail is required")
    private String detail;

    @NotBlank(message = "Summary is required")
    @Size(max = 255, message = "Summary must be less than 255 characters")
    private String summary;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}
