package com.management.lead.leadMangement.entitytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.management.lead.leadmangement.entity.User;

import jakarta.persistence.Table;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class UserEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEntityAnnotations() {
        Table tableAnnotation = User.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("users", tableAnnotation.name());
    }

    @Test
    void testFieldsAndValidations() {
        User user = new User();
        user.setName("Test User");
        user.setPassword("TestPassword");
        user.setEmail("test@example.com");
        user.setCompanyname("Test Company");
        user.setPhone("1234567890");
        user.setTeamname("Test Team");
        user.setRole("Test Role");

        var violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

}
