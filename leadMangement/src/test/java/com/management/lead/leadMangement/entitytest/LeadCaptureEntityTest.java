package com.management.lead.leadMangement.entitytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.management.lead.leadmangement.entity.LeadCapture;

import jakarta.persistence.Table;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class LeadCaptureEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEntityAnnotations() {
        Table tableAnnotation = LeadCapture.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("leadsCapture", tableAnnotation.name());
    }

    @Test
    void testFieldsAndValidations() {
        LeadCapture leadCapture = new LeadCapture();
        leadCapture.setName("Test Name");
        leadCapture.setEmail("test@example.com");
        leadCapture.setPhone("1234567890");

        var violations = validator.validate(leadCapture);
        assertTrue(violations.isEmpty());
    }

}
