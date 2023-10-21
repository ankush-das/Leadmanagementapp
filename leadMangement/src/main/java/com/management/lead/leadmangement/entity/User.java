package com.management.lead.leadmangement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Size(max = 255)
	private String name;

	@NotBlank
	@Size(min = 6, max = 255)
	private String password;

	@NotBlank
	@Email
	@Size(max = 255)
	private String email;

	@Size(max = 255)
	private String companyname;

	@NotBlank
	@Size(max = 20)
	private String phone;

	@NotBlank
	@Size(max = 255)
	private String teamname;

	private String role;
}
