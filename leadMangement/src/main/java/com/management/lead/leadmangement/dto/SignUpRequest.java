package com.management.lead.leadmangement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    private String companyname;

    @NotBlank
    private String phone;

    private String role;

    @NotBlank
    private String teamname;
}
