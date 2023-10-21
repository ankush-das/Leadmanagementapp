package com.management.lead.leadmangement.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "leadsCapture")
@Data
public class LeadCapture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 20)
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }
}
