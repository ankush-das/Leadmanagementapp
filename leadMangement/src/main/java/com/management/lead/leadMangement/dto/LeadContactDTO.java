package com.management.lead.leadMangement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LeadContactDTO {

    @NotBlank
    private String companyName;

    @NotBlank
    private String jobPosition;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String region;
}
