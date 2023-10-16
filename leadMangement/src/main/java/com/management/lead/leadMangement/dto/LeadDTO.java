package com.management.lead.leadMangement.dto;

import java.math.BigDecimal;
import java.util.Date;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LeadDTO {

    @NotBlank
    private String quote;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal budget;

    @NotBlank
    private String probability;

    @NotBlank
    private String priority;

    private String stage = "qualified"; // New ,qualified ,proposition ,negotiation ,Won

    @NotBlank
    private String source;

    @NotBlank
    private String tag; // type of deal

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date expectedClosingDate;

    @NotBlank
    private long assignedUser;
}
