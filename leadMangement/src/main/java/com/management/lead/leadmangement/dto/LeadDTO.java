package com.management.lead.leadmangement.dto;

import java.math.BigDecimal;
import java.util.Date;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
public class LeadDTO {

    private String quote;

    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal budget;

    private String probability;

    private String priority;

    private String stage;

    private String source;

    private String tag;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date expectedClosingDate;

    private long assignedUser;
}
