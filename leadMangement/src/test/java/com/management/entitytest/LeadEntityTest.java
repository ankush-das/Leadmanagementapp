package com.management.entitytest;

import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.enumConstants.LeadPriority;
import com.management.lead.leadmangement.enumConstants.LeadStage;
import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.entity.LeadContactInfo;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Table;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class LeadEntityTest {

    @Test
    void testEntityAnnotations() {
        Table tableAnnotation = Lead.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("leads", tableAnnotation.name());
    }

    @Test
    void testFieldsAnnotations() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Lead lead = new Lead();
        lead.setQuote("Test Quote");
        lead.setBudget(new BigDecimal("1000.00"));
        lead.setProbability("High");
        lead.setPriority(LeadPriority.HIGH);
        lead.setStage(LeadStage.NEW);
        lead.setSource("Web");
        lead.setTag("Test Tag");
        lead.setAssignedUser(new User());
        lead.setExpectedClosingDate(new Date());
        lead.setLeadContact(new LeadContactInfo());

        var violations = validator.validate(lead);
        assertTrue(violations.isEmpty());
    }
}
