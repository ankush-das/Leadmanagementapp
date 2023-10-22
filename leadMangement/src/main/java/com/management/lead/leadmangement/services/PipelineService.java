package com.management.lead.leadmangement.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.management.lead.leadmangement.entity.ActivityLog;
import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.enumconstants.LeadStage;
import com.management.lead.leadmangement.exception.InvalidStateException;
import com.management.lead.leadmangement.exception.LeadNotFoundException;
import com.management.lead.leadmangement.repository.ActivityLogRepository;
import com.management.lead.leadmangement.repository.LeadCaptureRepository;
import com.management.lead.leadmangement.repository.LeadRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PipelineService {

    private LeadRepo leadRepo;
    private LeadCaptureRepository leadCaptureRepository;
    private UserService userService;
    private ActivityLogRepository activityLogRepository;

    public PipelineService(@Autowired LeadRepo leadRepo, LeadCaptureRepository leadCaptureRepository,
            UserService userService, ActivityLogRepository activityLogRepository) {
        this.leadRepo = leadRepo;
        this.leadCaptureRepository = leadCaptureRepository;
        this.userService = userService;
        this.activityLogRepository = activityLogRepository;

    }

    public List<Lead> findByStage(LeadStage stage) {
        return leadRepo.findByStage(stage);
    }

    public List<Lead> findAll() {
        return leadRepo.findAll();
    }

    public List<Lead> findNewOpportunities() {
        return findByStage(LeadStage.NEW);
    }

    public List<Lead> findQualifiedOpportunities() {
        return findByStage(LeadStage.QUALIFIED);
    }

    public List<Lead> findPropositionOpportunities() {
        return findByStage(LeadStage.PROPOSITION);
    }

    public List<Lead> findNegotiationOpportunities() {
        return findByStage(LeadStage.NEGOTIATION);
    }

    public List<Lead> findWonOpportunities() {
        return findByStage(LeadStage.WON);
    }

    public boolean isValidNextTransition(LeadStage currentStage, LeadStage nextStage) {
        if (currentStage == null || nextStage == null) {
            return false;
        }

        switch (currentStage) {
            case NEW:
                return nextStage == LeadStage.QUALIFIED || nextStage == LeadStage.CLOSED_LOST;
            case QUALIFIED:
                return nextStage == LeadStage.PROPOSITION || nextStage == LeadStage.CLOSED_LOST;
            case PROPOSITION:
                return nextStage == LeadStage.NEGOTIATION || nextStage == LeadStage.CLOSED_LOST;
            case NEGOTIATION:
                return nextStage == LeadStage.WON || nextStage == LeadStage.CLOSED_LOST;
            case WON:
                return nextStage == LeadStage.CLOSED_WON;
            case CLOSED_LOST:
            case CLOSED_WON:
                return false; // Disallow transitions from closed states
            default:
                return false; // Disallow any other unknown transitions
        }
    }

    public boolean isValidPrevTransition(LeadStage currentStage, LeadStage prevStage) {
        if (currentStage == null || prevStage == null) {
            return false; // Disallow transitions involving null stages
        }

        switch (currentStage) {
            case QUALIFIED:
                return prevStage == LeadStage.NEW || prevStage == LeadStage.CLOSED_LOST;
            case PROPOSITION:
                return prevStage == LeadStage.QUALIFIED || prevStage == LeadStage.CLOSED_LOST;
            case NEGOTIATION:
                return prevStage == LeadStage.PROPOSITION || prevStage == LeadStage.CLOSED_LOST;
            case WON:
                return prevStage == LeadStage.NEGOTIATION || prevStage == LeadStage.CLOSED_WON;
            case CLOSED_LOST:
            case CLOSED_WON:
                return false; // Disallow transitions from closed states
            default:
                return false; // Disallow any other unknown transitions
        }
    }

    public LeadStage calculateNextStage(LeadStage currentStage) {
        switch (currentStage) {
            case NEW:
                return LeadStage.QUALIFIED;
            case QUALIFIED:
                return LeadStage.PROPOSITION;
            case PROPOSITION:
                return LeadStage.NEGOTIATION;
            case NEGOTIATION:
                return LeadStage.WON;
            case WON:
                return LeadStage.CLOSED_WON;
            case CLOSED_LOST:
            case CLOSED_WON:
                return currentStage; // Stay in the same state for closed states
            default:
                return currentStage; // Stay in the same state for unknown states
        }
    }

    public LeadStage calculatePreviousStage(LeadStage currentStage) {
        switch (currentStage) {
            case QUALIFIED:
                return LeadStage.NEW;
            case PROPOSITION:
                return LeadStage.QUALIFIED;
            case NEGOTIATION:
                return LeadStage.PROPOSITION;
            case WON:
                return LeadStage.NEGOTIATION;
            default:
                return currentStage; // Stay in the same state for other states
        }
    }

    public Lead nextstageChange(Long leadId) {
        // capture lead
        Optional<Lead> leadOptional = leadRepo.findById(leadId);
        Optional<LeadCapture> leadCaptureOptional = leadCaptureRepository.findById(leadId);

        // get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> loggedInUser = userService.getByName(username);

        if (leadOptional.isPresent()) {
            Lead lead = leadOptional.get();
            LeadStage currentStage = lead.getStage();

            // Calculate the next stage
            LeadStage nextStage = calculateNextStage(currentStage);

            if (isValidNextTransition(currentStage, nextStage)) {
                lead.setStage(nextStage);
                leadRepo.save(lead);

                // create an activity log for this
                ActivityLog activityLog = new ActivityLog();
                activityLog.setLogDate(new Date());

                if (leadCaptureOptional.isPresent()) {
                    activityLog.setLead(leadCaptureOptional.get());
                }

                if (loggedInUser.isPresent()) {
                    activityLog.setUser(loggedInUser.get());
                    activityLog
                            .setDetails("Lead moved from " + currentStage + " to " + nextStage + " by "
                                    + loggedInUser.get().getName());
                }
                activityLogRepository.save(activityLog);
                return lead;
            } else {
                throw new InvalidStateException("Invalid state transition.");
            }
        } else {
            throw new EntityNotFoundException("Lead with ID " + leadId + " not found.");
        }
    }

    public Lead previousStageChange(Long leadId) {
        Optional<Lead> leadOptional = leadRepo.findById(leadId);
        Optional<LeadCapture> leadCaptureOptional = leadCaptureRepository.findById(leadId);

        // get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> loggedInUser = userService.getByName(username);

        if (leadOptional.isPresent()) {
            Lead lead = leadOptional.get();
            LeadStage currentStage = lead.getStage();

            // Calculate the previous stage
            LeadStage previousStage = calculatePreviousStage(currentStage); // Implement this method

            if (isValidPrevTransition(currentStage, previousStage)) {
                lead.setStage(previousStage);
                leadRepo.save(lead);

                // create an activity log for this
                ActivityLog activityLog = new ActivityLog();
                activityLog.setLogDate(new Date());

                if (leadCaptureOptional.isPresent()) {
                    activityLog.setLead(leadCaptureOptional.get());
                }

                if (loggedInUser.isPresent()) {
                    activityLog.setUser(loggedInUser.get());
                    activityLog
                            .setDetails("Lead moved from " + currentStage + " to " + previousStage + " by "
                                    + loggedInUser.get().getName());
                }
                activityLogRepository.save(activityLog);
                return lead;
            } else {
                throw new InvalidStateException("Invalid state transition.");
            }
        } else {
            throw new EntityNotFoundException("Lead with ID " + leadId + " not found.");
        }
    }

    private boolean isTransitionToLostValid(Lead lead) {
        LeadStage currentStage = lead.getStage();

        if (currentStage == LeadStage.CLOSED_LOST || currentStage == LeadStage.CLOSED_WON) {
            return false;
        }

        return currentStage == LeadStage.NEGOTIATION ||
                currentStage == LeadStage.QUALIFIED ||
                currentStage == LeadStage.NEW ||
                currentStage == LeadStage.WON;
    }

    public Lead lostStageChange(Long leadId) {
        Optional<Lead> leadOptional = leadRepo.findById(leadId);

        if (leadOptional.isPresent()) {
            Lead lead = leadOptional.get();

            if (isTransitionToLostValid(lead)) {
                lead.setStage(LeadStage.CLOSED_LOST);
                return leadRepo.save(lead);
            } else {
                throw new InvalidStateException("Invalid state transition to 'Lost'.");
            }
        } else {
            throw new EntityNotFoundException("Lead not found.");
        }
    }

    public Lead closedWONStageChange(Long leadId) {
        Optional<Lead> leadOptional = leadRepo.findById(leadId);

        if (leadOptional.isEmpty()) {
            throw new LeadNotFoundException("Lead not found for ID: " + leadId);
        }

        Lead lead = leadOptional.get();

        lead.setStage(LeadStage.CLOSED_WON);

        leadRepo.save(lead);

        return lead;
    }

    // search lead by company name
    public List<Lead> searchLeadsByCompanyName(String companyName) {
        return leadRepo.findByLeadContactCompanyName(companyName);
    }

    // search lead by sources
    public List<Lead> searchLeadsBySource(String source) {
        return leadRepo.findBySource(source);
    }
}
