package com.management.lead.leadMangement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.lead.leadmangement.LeadMangementApplication;
import com.management.lead.leadmangement.dto.SignUpRequest;
import com.management.lead.leadmangement.repository.UserRepository;
import com.management.lead.leadmangement.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = LeadMangementApplication.class)
public class leadmanagementintegrationtest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + "/api/auth";
    }

    @Test
    // @Sql(statements = "INSERT INTO users (name, password, email, companyname,
    // phone, teamname, role) VALUES ('John Doe', 'password123',
    // 'john.doe@example.com', 'ACME Inc', '1234567890', 'Sales Team', 'USER')")
    @Sql(statements = "DELETE FROM users WHERE name='newuser'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testRegisterUser() throws Exception {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("newuser");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("newuser@example.com");
        signUpRequest.setPhone("1234567890");
        signUpRequest.setCompanyname("Company");
        signUpRequest.setTeamname("Team");

        ResponseEntity<String> response = restTemplate.exchange(
                (createURLWithPort() + "/signup"), HttpMethod.POST, new HttpEntity<>(signUpRequest, headers),
                String.class);
        String responseBody = response.getBody();
        assertEquals("User registered successfully", responseBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
