package com.management.lead.leadMangement.dto;

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

    @NotBlank
    private String companyname;

    @NotBlank
    private String phone;

    private String role;

    @NotBlank
    private String teamname;
}
