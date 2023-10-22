package com.management.lead.leadMangement.servicetest;

import com.management.lead.leadmangement.dto.LeadCaptureDTO;
import com.management.lead.leadmangement.dto.LeadContactDTO;
import com.management.lead.leadmangement.dto.LeadDTO;
import com.management.lead.leadmangement.entity.ActivityLog;
import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.LeadContactInfo;
import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.enumconstants.LeadPriority;
import com.management.lead.leadmangement.enumconstants.LeadStage;
import com.management.lead.leadmangement.repository.ActivityLogRepository;
import com.management.lead.leadmangement.repository.LeadCaptureRepository;
import com.management.lead.leadmangement.repository.LeadContactInfoRepository;
import com.management.lead.leadmangement.repository.LeadRepo;
import com.management.lead.leadmangement.repository.UserRepository;
import com.management.lead.leadmangement.services.LeadService;
import com.management.lead.leadmangement.services.UserService;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeadServiceTest {

    @InjectMocks
    private LeadService leadService;

    @Mock
    private LeadCaptureRepository leadCaptureRepository;

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private LeadContactInfoRepository leadContactInfoRepository;

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Mock
    private LeadRepo leadRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        Mockito.reset(leadCaptureRepository, emailSender);
    }

    @Test
    void testCreateCapturedLead() {
        LeadCaptureDTO leadCaptureDTO = new LeadCaptureDTO();
        leadCaptureDTO.setName("John Doe");
        leadCaptureDTO.setEmail("johndoe@example.com");
        leadCaptureDTO.setPhone("123-456-7890");

        LeadCapture capturedLead = new LeadCapture();
        capturedLead.setId(1L); // Set an ID for the captured lead
        capturedLead.setName(leadCaptureDTO.getName());
        capturedLead.setEmail(leadCaptureDTO.getEmail());
        capturedLead.setPhone(leadCaptureDTO.getPhone());
        capturedLead.setCreatedDate(new Date());

        // Mock the behavior of leadCaptureRepository.save
        when(leadCaptureRepository.save(Mockito.any(LeadCapture.class))).thenReturn(capturedLead);

        // Call the method to create the captured lead
        LeadCapture createdLead = leadService.createCapturedLead(leadCaptureDTO);

        // Verify that leadCaptureRepository.save is called with the correct lead
        // capture
        Mockito.verify(leadCaptureRepository).save(Mockito.any(LeadCapture.class));

        // Verify that the created lead matches the expected values
        assertLeadCaptureEquals(leadCaptureDTO, createdLead);
    }

    @Test
    void testUpdateCapturedLead() {
        Long leadCaptureId = 1L;
        LeadCaptureDTO leadCaptureDTO = new LeadCaptureDTO();
        leadCaptureDTO.setName("Updated Name");
        leadCaptureDTO.setEmail("updated@example.com");
        leadCaptureDTO.setPhone("987-654-3210");

        LeadCapture existingLeadCapture = new LeadCapture();
        existingLeadCapture.setId(leadCaptureId);
        existingLeadCapture.setName("Original Name");
        existingLeadCapture.setEmail("original@example.com");
        existingLeadCapture.setPhone("123-456-7890");

        // Mock the behavior of leadCaptureRepository.findById
        when(leadCaptureRepository.findById(leadCaptureId)).thenReturn(Optional.of(existingLeadCapture));

        // Mock the behavior of leadCaptureRepository.save
        when(leadCaptureRepository.save(Mockito.any(LeadCapture.class))).thenReturn(existingLeadCapture);

        // Call the method to update the captured lead
        LeadCapture updatedLeadCapture = leadService.updateCapturedLead(leadCaptureId, leadCaptureDTO);

        // Verify that leadCaptureRepository.findById is called with the correct
        // leadCaptureId
        Mockito.verify(leadCaptureRepository).findById(leadCaptureId);

        // Verify that leadCaptureRepository.save is called with the updated lead
        // capture
        Mockito.verify(leadCaptureRepository).save(existingLeadCapture);

        // Verify that the updated lead capture matches the expected values
        assertLeadCaptureEquals(leadCaptureDTO, updatedLeadCapture);
    }

    @Test
    void testUpdateCapturedLead_EntityNotFound() {
        Long leadCaptureId = 1L;
        LeadCaptureDTO leadCaptureDTO = new LeadCaptureDTO();
        leadCaptureDTO.setName("Updated Name");
        leadCaptureDTO.setEmail("updated@example.com");
        leadCaptureDTO.setPhone("987-654-3210");

        // Mock the behavior of leadCaptureRepository.findById to return an empty
        // optional
        when(leadCaptureRepository.findById(leadCaptureId)).thenReturn(Optional.empty());

        // Verify that an EntityNotFoundException is thrown when attempting to update a
        // non-existent lead
        assertThrows(EntityNotFoundException.class,
                () -> leadService.updateCapturedLead(leadCaptureId, leadCaptureDTO));
    }

    @Test
    void testDeleteLeadCapture() {
        Long leadCaptureId = 1L;
        LeadCapture existingLeadCapture = new LeadCapture();
        existingLeadCapture.setId(leadCaptureId);

        // Mock the behavior of leadCaptureRepository.findById
        when(leadCaptureRepository.findById(leadCaptureId)).thenReturn(Optional.of(existingLeadCapture));

        // Call the method to delete the lead capture
        leadService.deleteLeadCapture(leadCaptureId);

        // Verify that leadCaptureRepository.findById is called with the correct
        // leadCaptureId
        Mockito.verify(leadCaptureRepository).findById(leadCaptureId);

        // Verify that leadCaptureRepository.delete is called with the existing lead
        // capture
        Mockito.verify(leadCaptureRepository).delete(existingLeadCapture);
    }

    @Test
    void testDeleteLeadCapture_EntityNotFound() {
        Long leadCaptureId = 1L;

        // Mock the behavior of leadCaptureRepository.findById to return an empty
        // optional
        when(leadCaptureRepository.findById(leadCaptureId)).thenReturn(Optional.empty());

        // Verify that an EntityNotFoundException is thrown when attempting to delete a
        // non-existent lead capture
        assertThrows(EntityNotFoundException.class, () -> leadService.deleteLeadCapture(leadCaptureId));
    }

    // Helper method to assert that the updated or created lead capture matches the
    // expected values
    private void assertLeadCaptureEquals(LeadCaptureDTO expected, LeadCapture actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPhone(), actual.getPhone());
    }

    @Test
    void testCreateLeadContactInfo() {
        long capturedLeadId = 1L;
        LeadContactDTO leadContactDTO = new LeadContactDTO();
        leadContactDTO.setCompanyName("Company XYZ");
        leadContactDTO.setJobPosition("Manager");
        leadContactDTO.setAddress("123 Main St");
        leadContactDTO.setCity("City");
        leadContactDTO.setState("State");
        leadContactDTO.setPostalCode("12345");
        leadContactDTO.setRegion("Region");

        LeadCapture leadCapture = new LeadCapture();
        leadCapture.setId(capturedLeadId);

        // Mock the behavior of leadCaptureRepository.findById
        when(leadCaptureRepository.findById(capturedLeadId)).thenReturn(Optional.of(leadCapture));

        // Mock the behavior of leadContactInfoRepository.save
        when(leadContactInfoRepository.save(Mockito.any(LeadContactInfo.class))).thenAnswer(invocation -> {
            LeadContactInfo savedContactInfo = invocation.getArgument(0);
            savedContactInfo.setId(2L); // Set a unique ID for the saved contact info
            return savedContactInfo;
        });

        // Call the method to create lead contact info
        LeadContactInfo createdContactInfo = leadService.createLeadContactInfo(capturedLeadId, leadContactDTO);

        // Verify that leadCaptureRepository.findById is called with the correct
        // capturedLeadId
        Mockito.verify(leadCaptureRepository).findById(capturedLeadId);

        // Verify that leadContactInfoRepository.save is called with the created contact
        // info
        Mockito.verify(leadContactInfoRepository).save(Mockito.any(LeadContactInfo.class));

        // Verify that the created lead contact info matches the expected values
        assertLeadContactInfoEquals(leadContactDTO, createdContactInfo);
    }

    @Test
    void testCreateLeadContactInfo_EntityNotFound() {
        long capturedLeadId = 1L;
        LeadContactDTO leadContactDTO = new LeadContactDTO();

        // Mock the behavior of leadCaptureRepository.findById to return an empty
        // optional
        when(leadCaptureRepository.findById(capturedLeadId)).thenReturn(Optional.empty());

        // Verify that an EntityNotFoundException is thrown when attempting to create
        // contact info for a non-existent lead
        assertThrows(EntityNotFoundException.class,
                () -> leadService.createLeadContactInfo(capturedLeadId, leadContactDTO));
    }

    @Test
    void testGetAllLeadContacts() {
        List<LeadContactInfo> leadContacts = new ArrayList<>();
        leadContacts.add(new LeadContactInfo());
        leadContacts.add(new LeadContactInfo());

        // Mock the behavior of leadContactInfoRepository.findAll to return the list of
        // lead contacts
        when(leadContactInfoRepository.findAll()).thenReturn(leadContacts);

        // Call the method to get all lead contacts
        List<LeadContactInfo> retrievedContacts = leadService.getAllLeadContacts();

        // Verify that leadContactInfoRepository.findAll is called
        Mockito.verify(leadContactInfoRepository).findAll();

        // Verify that the retrieved list of lead contacts matches the expected list
        assertEquals(leadContacts, retrievedContacts);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        // Mock the behavior of userRepository.findAll to return the list of users
        when(userRepository.findAll()).thenReturn(users);

        // Call the method to get all users
        List<User> retrievedUsers = leadService.getAllUsers();

        // Verify that userRepository.findAll is called
        Mockito.verify(userRepository).findAll();

        // Verify that the retrieved list of users matches the expected list
        assertEquals(users, retrievedUsers);
    }

    // Helper method to assert that the created or updated lead contact info matches
    // the expected values
    private void assertLeadContactInfoEquals(LeadContactDTO expected, LeadContactInfo actual) {
        assertEquals(expected.getCompanyName(), actual.getCompanyName());
        assertEquals(expected.getJobPosition(), actual.getJobPosition());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getState(), actual.getState());
        assertEquals(expected.getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getRegion(), actual.getRegion());
    }

    @Test
    void testUpdateLeadContactInfo() {
        LeadContactDTO leadContactDTO = new LeadContactDTO();
        leadContactDTO.setCompanyName("Updated Company");

        LeadContactInfo existingContactInfo = new LeadContactInfo();
        existingContactInfo.setId(1L);
        existingContactInfo.setCompanyName("Original Company");

        when(leadContactInfoRepository.findById(1L)).thenReturn(Optional.of(existingContactInfo));

        when(leadContactInfoRepository.save(any(LeadContactInfo.class))).thenAnswer(invocation -> {
            LeadContactInfo savedContactInfo = invocation.getArgument(0);
            return savedContactInfo;
        });

        assertDoesNotThrow(() -> {
            LeadContactInfo updatedContactInfo = leadService.updateLeadContactInfo(1L, leadContactDTO);
            assertNotNull(updatedContactInfo);
            assertEquals("Updated Company", updatedContactInfo.getCompanyName());
            assertEquals(1L, updatedContactInfo.getId());
        });
    }

    @Test
    void testUpdateLeadContactInfo_EntityNotFound() {
        when(leadContactInfoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            leadService.updateLeadContactInfo(1L, new LeadContactDTO());
        });
    }

    @Test
    void testDeleteLeadContactInfo() {
        when(leadContactInfoRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> {
            leadService.deleteLeadContactInfo(1L);
            verify(leadContactInfoRepository).deleteById(1L);
        });
    }

    @Test
    void testDeleteLeadContactInfo_EntityNotFound() {
        when(leadContactInfoRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            leadService.deleteLeadContactInfo(1L);
        });
    }

    @Test
    void testCreateLead_LeadCaptureNotFound() {
        when(leadCaptureRepository.findById(1L)).thenReturn(Optional.empty());

        LeadDTO leadDTO = new LeadDTO();
        assertThrows(EntityNotFoundException.class, () -> {
            leadService.createLead(1L, leadDTO);
        });
    }

    @Test
    void testCreateLead_LeadContactInfoNotFound() {
        LeadCapture capturedLead = new LeadCapture();
        capturedLead.setId(1L);
        when(leadCaptureRepository.findById(1L)).thenReturn(Optional.of(capturedLead));

        when(leadContactInfoRepository.findById(1L)).thenReturn(Optional.empty());

        LeadDTO leadDTO = new LeadDTO();
        assertThrows(EntityNotFoundException.class, () -> {
            leadService.createLead(1L, leadDTO);
        });
    }

    @Test
    void testCreateLead_NoLoggedInUser() {
        when(leadCaptureRepository.findById(1L)).thenReturn(Optional.empty());

        LeadDTO leadDTO = new LeadDTO();
        assertThrows(EntityNotFoundException.class, () -> {
            leadService.createLead(1L, leadDTO);
        });
    }
}
