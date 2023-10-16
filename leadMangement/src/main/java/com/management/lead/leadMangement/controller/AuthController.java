package com.management.lead.leadMangement.controller;

import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.management.lead.leadMangement.dto.JwtResponse;
import com.management.lead.leadMangement.dto.LoginRequest;
import com.management.lead.leadMangement.dto.SignUpRequest;
import com.management.lead.leadMangement.entity.User;
import com.management.lead.leadMangement.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private AuthenticationManager authenticationManager;
  private UserService userService;
  private JwtEncoder jwtEncoder;

  public AuthController(@Autowired AuthenticationManager authenticationManager, UserService userService,
      JwtEncoder jwtEncoder) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.jwtEncoder = jwtEncoder;
  }

  // Login Endpoint
  @PostMapping("/token")
  ResponseEntity<?> token(@RequestBody LoginRequest loginBody) {
    Instant now = Instant.now();
    long expiry = 3600L;
    var username = loginBody.getUsername();
    var password = loginBody.getPassword();
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
    JwtResponse response = new JwtResponse(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
        authentication.getName(), authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" ")));
    return ResponseEntity.ok(response);
  }

  // Register Endpoint
  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@RequestBody SignUpRequest signUpRequest) {
    try {

      if (userService.existsByName(signUpRequest.getUsername())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
      }
      User user = new User();
      user.setName(signUpRequest.getUsername());
      user.setPassword(signUpRequest.getPassword());
      user.setEmail(signUpRequest.getEmail());
      user.setPhone(signUpRequest.getPhone());
      user.setCompanyname(signUpRequest.getCompanyname());
      user.setTeamname(signUpRequest.getTeamname());

      if (signUpRequest.getRole() == null) {
        signUpRequest.setRole("USER");
      }
      user.setRole(signUpRequest.getRole());

      userService.create(user);
      return ResponseEntity.ok("User registered successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
    }
  }
}
