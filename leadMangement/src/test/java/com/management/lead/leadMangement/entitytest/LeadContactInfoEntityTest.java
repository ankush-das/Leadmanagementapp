package com.management.lead.leadMangement.entitytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.LeadContactInfo;

import jakarta.persistence.Table;

class LeadContactInfoEntityTest {

    @Test
    void testEntityAnnotations() {
        Table tableAnnotation = LeadContactInfo.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("lead_contact_data", tableAnnotation.name());
    }

    @Test
    void testFieldsAndAssociations() {
        LeadCapture leadCapture = new LeadCapture();
        leadCapture.setId(1);

        LeadContactInfo leadContactInfo = new LeadContactInfo();
        leadContactInfo.setCompanyName("Test Company");
        leadContactInfo.setJobPosition("Manager");
        leadContactInfo.setAddress("123 Main St");
        leadContactInfo.setCity("City");
        leadContactInfo.setState("State");
        leadContactInfo.setPostalCode("12345");
        leadContactInfo.setRegion("Test Region");
        leadContactInfo.setLeadCaptureData(leadCapture);

        assertEquals("Test Company", leadContactInfo.getCompanyName());
        assertEquals("Manager", leadContactInfo.getJobPosition());
        assertEquals("123 Main St", leadContactInfo.getAddress());
        assertEquals("City", leadContactInfo.getCity());
        assertEquals("State", leadContactInfo.getState());
        assertEquals("12345", leadContactInfo.getPostalCode());
        assertEquals("Test Region", leadContactInfo.getRegion());
        assertEquals(leadCapture, leadContactInfo.getLeadCaptureData());
    }

}
