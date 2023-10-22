package com.management.lead.leadMangement.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.enumconstants.LeadStage;
import com.management.lead.leadmangement.exception.InvalidStateException;
import com.management.lead.leadmangement.repository.LeadRepo;
import com.management.lead.leadmangement.services.PipelineService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class PipelineServiceTest {

    @InjectMocks
    private PipelineService pipelineService;

    @Mock
    private LeadRepo leadRepo;

    @BeforeEach
    void setUp() {
        Mockito.reset(leadRepo);
    }

    @Test
    void testFindAll() {
        List<Lead> leads = new ArrayList<>();
        leads.add(new Lead());
        leads.add(new Lead());

        when(leadRepo.findAll()).thenReturn(leads);

        List<Lead> result = pipelineService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindNewOpportunities() {
        List<Lead> leads = new ArrayList<>();
        leads.add(new Lead());
        leads.add(new Lead());

        when(leadRepo.findByStage(LeadStage.NEW)).thenReturn(leads);

        List<Lead> result = pipelineService.findNewOpportunities();

        assertEquals(2, result.size());
    }

    @Test
    void testNextStageChange_ValidTransition() {
        Long leadId = 1L;
        Lead lead = new Lead();
        lead.setId(leadId);
        lead.setStage(LeadStage.NEW);

        when(leadRepo.findById(leadId)).thenReturn(Optional.of(lead));

        Lead result = pipelineService.nextstageChange(leadId);

        assertEquals(LeadStage.QUALIFIED, result.getStage());
    }

    @Test
    void testNextStageChange_InvalidTransition() {
        Long leadId = 1L;
        Lead lead = new Lead();
        lead.setId(leadId);
        lead.setStage(LeadStage.CLOSED_LOST);

        when(leadRepo.findById(leadId)).thenReturn(Optional.of(lead));

        assertThrows(InvalidStateException.class, () -> pipelineService.nextstageChange(leadId));
    }

    @Test
    void testPreviousStageChange_ValidTransition() {
        Long leadId = 1L;
        Lead lead = new Lead();
        lead.setId(leadId);
        lead.setStage(LeadStage.PROPOSITION);

        when(leadRepo.findById(leadId)).thenReturn(Optional.of(lead));

        Lead result = pipelineService.previousStageChange(leadId);

        assertEquals(LeadStage.QUALIFIED, result.getStage());
    }

    @Test
    void testPreviousStageChange_InvalidTransition() {
        Long leadId = 1L;
        Lead lead = new Lead();
        lead.setId(leadId);
        lead.setStage(LeadStage.CLOSED_WON);

        when(leadRepo.findById(leadId)).thenReturn(Optional.of(lead));

        assertThrows(InvalidStateException.class, () -> pipelineService.previousStageChange(leadId));
    }

    @Test
    public void testLostStageChange_ValidTransition() {
        Long leadId = 1L;
        Lead lead = new Lead();
        lead.setId(leadId);
        lead.setStage(LeadStage.NEW);

        when(leadRepo.findById(leadId)).thenReturn(Optional.of(lead));
        when(leadRepo.save(lead)).thenReturn(lead);

        Lead result = pipelineService.lostStageChange(leadId);

        assertEquals(LeadStage.CLOSED_LOST, result.getStage());
    }

    @Test
    public void testLostStageChange_InvalidTransition() {
        Long leadId = 1L;
        Lead lead = new Lead();
        lead.setId(leadId);
        lead.setStage(LeadStage.CLOSED_WON);

        when(leadRepo.findById(leadId)).thenReturn(Optional.of(lead));

        assertThrows(InvalidStateException.class, () -> pipelineService.lostStageChange(leadId));
    }

    @Test
    public void testLostStageChange_LeadNotFound() {
        Long leadId = 1L;

        when(leadRepo.findById(leadId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pipelineService.lostStageChange(leadId));
    }
}
