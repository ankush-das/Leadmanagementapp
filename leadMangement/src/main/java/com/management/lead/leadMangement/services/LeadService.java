package com.management.lead.leadMangement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.management.lead.leadMangement.dto.LeadCaptureDTO;
import com.management.lead.leadMangement.dto.LeadContactDTO;
import com.management.lead.leadMangement.dto.LeadDTO;
import com.management.lead.leadMangement.entity.LeadCapture;
import com.management.lead.leadMangement.entity.LeadContactInfo;
import com.management.lead.leadMangement.entity.User;
import com.management.lead.leadMangement.enumConstants.LeadPriority;
import com.management.lead.leadMangement.enumConstants.LeadStage;
import com.management.lead.leadMangement.entity.ActivityLog;
import com.management.lead.leadMangement.entity.Lead;
import com.management.lead.leadMangement.repository.LeadContactInfoRepository;
import com.management.lead.leadMangement.repository.LeadRepo;
import com.management.lead.leadMangement.repository.ActivityLogRepository;
import com.management.lead.leadMangement.repository.LeadCaptureRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.Optional;

@Service
public class LeadService {

    private LeadCaptureRepository leadCaptureRepository;
    private UserService userService;
    private LeadContactInfoRepository leadContactInfoRepository;
    private LeadRepo leadRepo;
    private ActivityLogRepository activityLogRepository;
    private JavaMailSender emailSender;

    public LeadService(@Autowired LeadCaptureRepository leadCaptureRepository, UserService userService,
            LeadContactInfoRepository leadContactInfoRepository, LeadRepo leadRepo, JavaMailSender emailSender,
            ActivityLogRepository activityLogRepository) {
        this.leadCaptureRepository = leadCaptureRepository;
        this.userService = userService;
        this.leadContactInfoRepository = leadContactInfoRepository;
        this.leadRepo = leadRepo;
        this.activityLogRepository = activityLogRepository;
        this.emailSender = emailSender;
    }

    public LeadCapture createCapturedLead(LeadCaptureDTO leadCaptureDTO) {

        LeadCapture leadCapture = new LeadCapture();
        leadCapture.setName(leadCaptureDTO.getName());
        leadCapture.setEmail(leadCaptureDTO.getEmail());
        leadCapture.setPhone(leadCaptureDTO.getPhone());
        leadCapture.setCreatedDate(new Date());

        // Save the lead capture record to the database
        leadCapture = leadCaptureRepository.save(leadCapture);
        // String leadId = String.valueOf(leadCapture.getId());
        // String baseUrl = "http://localhost:4200/";

        // String message = "Please fill out the contact details at link" + baseUrl +
        // leadId;
        // // Send an email to the captured lead
        // sendEmailToLead(leadCaptureDTO.getEmail(), "Thank you for your interest",
        // message);

        return leadCapture;
    }

    // private void sendEmailToLead(String toEmail, String subject, String body) {
    // SimpleMailMessage message = new SimpleMailMessage();
    // message.setFrom("dasankush1511@gmail.com");
    // message.setTo(toEmail);
    // message.setSubject(subject);
    // message.setText(body);

    // emailSender.send(message);
    // }

    public LeadCapture updateCapturedLead(Long leadCaptureId, LeadCaptureDTO leadCaptureDTO) {
        // Find the existing LeadCapture entity by its ID
        LeadCapture leadCapture = leadCaptureRepository.findById(leadCaptureId)
                .orElseThrow(() -> new EntityNotFoundException("LeadCapture with ID " +
                        leadCaptureId + " not found"));

        // Update the properties with the values from the DTO
        leadCapture.setName(leadCaptureDTO.getName());
        leadCapture.setEmail(leadCaptureDTO.getEmail());
        leadCapture.setPhone(leadCaptureDTO.getPhone());

        // Save the updated entity
        return leadCaptureRepository.save(leadCapture);
    }

    public void deleteLeadCapture(Long leadCaptureId) {
        // Attempt to find the existing LeadCapture entity by its ID
        LeadCapture leadCapture = leadCaptureRepository.findById(leadCaptureId)
                .orElseThrow(() -> new EntityNotFoundException("LeadCapture with ID " +
                        leadCaptureId + " not found"));

        // Perform the deletion
        leadCaptureRepository.delete(leadCapture);
    }

    public LeadContactInfo createLeadContactInfo(long capturedLeadId, LeadContactDTO leadcontactDTO) {
        // Retrieve the capturedLead entity based on the leadId
        LeadCapture leadCaptureData = leadCaptureRepository.findById(capturedLeadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " + capturedLeadId + " not found"));

        LeadContactInfo contactInfo = new LeadContactInfo();
        contactInfo.setCompanyName(leadcontactDTO.getCompanyName());
        contactInfo.setJobPosition(leadcontactDTO.getJobPosition());
        contactInfo.setAddress(leadcontactDTO.getAddress());
        contactInfo.setCity(leadcontactDTO.getCity());
        contactInfo.setState(leadcontactDTO.getState());
        contactInfo.setPostalCode(leadcontactDTO.getPostalCode());
        contactInfo.setRegion(leadcontactDTO.getRegion());
        contactInfo.setLeadCaptureData(leadCaptureData);

        return leadContactInfoRepository.save(contactInfo);
    }

    public LeadContactInfo updateLeadContactInfo(long contactInfoId, LeadContactDTO leadContactDTO) {
        // Retrieve the existing LeadContactInfo entity based on contactInfoId
        LeadContactInfo existingContactInfo = leadContactInfoRepository.findById(contactInfoId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Lead Contact Info with ID " + contactInfoId + " not found"));

        // Update the properties with values from the DTO
        existingContactInfo.setCompanyName(leadContactDTO.getCompanyName());
        existingContactInfo.setJobPosition(leadContactDTO.getJobPosition());
        existingContactInfo.setAddress(leadContactDTO.getAddress());
        existingContactInfo.setCity(leadContactDTO.getCity());
        existingContactInfo.setState(leadContactDTO.getState());
        existingContactInfo.setPostalCode(leadContactDTO.getPostalCode());
        existingContactInfo.setRegion(leadContactDTO.getRegion());

        // Save the updated LeadContactInfo
        return leadContactInfoRepository.save(existingContactInfo);
    }

    public void deleteLeadContactInfo(long contactInfoId) {
        // Check if the LeadContactInfo with the given ID exists
        if (!leadContactInfoRepository.existsById(contactInfoId)) {
            throw new EntityNotFoundException("Lead Contact Info with ID " + contactInfoId + " not found");
        }

        // Delete the LeadContactInfo with the given ID
        leadContactInfoRepository.deleteById(contactInfoId);
    }

    public Lead createLead(long leadId, LeadDTO leadDTO) {

        LeadCapture capturedLead = leadCaptureRepository.findById(leadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " + leadId + " not found"));

        LeadContactInfo leadContact = leadContactInfoRepository.findById(leadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " + leadId + " not found"));

        // get assigned user
        Optional<User> userOptional = userService.getById(leadDTO.getAssignedUser());
        User assignedUser = userOptional.orElseThrow(() -> new EntityNotFoundException("User not found"));

        // get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> loggedInUser = userService.getByName(username);

        Lead lead = new Lead();
        lead.setQuote(leadDTO.getQuote());
        lead.setBudget(leadDTO.getBudget());
        lead.setProbability(leadDTO.getProbability());
        lead.setPriority(LeadPriority.valueOf(leadDTO.getPriority()));
        lead.setStage(LeadStage.valueOf(leadDTO.getStage()));
        lead.setSource(leadDTO.getSource());
        lead.setTag(leadDTO.getTag());
        lead.setExpectedClosingDate(leadDTO.getExpectedClosingDate());
        lead.setLeadCaptureData(capturedLead);
        lead.setLeadContact(leadContact);
        lead.setAssignedUser(assignedUser);

        // create an activity log for this
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLogDate(new Date());
        activityLog.setLead(capturedLead);
        activityLog.setUser(assignedUser);

        if (loggedInUser.isPresent()) {
            activityLog.setDetails("Lead has been Created by" + loggedInUser.get().getName() + "and Assigned to "
                    + assignedUser.getName());
        }

        activityLogRepository.save(activityLog);

        return leadRepo.save(lead);
    }

    public Lead updateLead(long leadId, LeadDTO leadDTO) {

        Lead leadToUpdate = leadRepo.findById(leadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " + leadId + " not found"));

        LeadCapture capturedLead = leadCaptureRepository.findById(leadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " + leadId + " not found"));

        // get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> loggedInUser = userService.getByName(username);

        // get assigned user
        Optional<User> userOptional = userService.getById(leadDTO.getAssignedUser());
        User assignedUser = userOptional.orElseThrow(() -> new EntityNotFoundException("User not found"));

        leadToUpdate.setQuote(leadDTO.getQuote());
        leadToUpdate.setBudget(leadDTO.getBudget());
        leadToUpdate.setProbability(leadDTO.getProbability());
        leadToUpdate.setPriority(LeadPriority.valueOf(leadDTO.getPriority()));
        leadToUpdate.setSource(leadDTO.getSource());
        leadToUpdate.setStage(LeadStage.valueOf(leadDTO.getStage()));
        leadToUpdate.setTag(leadDTO.getTag());
        leadToUpdate.setExpectedClosingDate(leadDTO.getExpectedClosingDate());
        leadToUpdate.setAssignedUser(assignedUser);

        // create an activity log for lead update
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLogDate(new Date());
        activityLog.setLead(capturedLead);
        activityLog.setUser(assignedUser);

        if (loggedInUser.isPresent()) {
            activityLog.setDetails(
                    "Lead has been updated and assigned to " + assignedUser.getName() + "by "
                            + loggedInUser.get().getName());
        }

        activityLogRepository.save(activityLog);

        return leadRepo.save(leadToUpdate);
    }

    public void deleteLead(long leadId) {
        // Check if the opportunity exists
        if (!leadRepo.existsById(leadId)) {
            throw new EntityNotFoundException("Lead with ID " + leadId + " not found");
        }

        // Delete the opportunity
        leadRepo.deleteById(leadId);
    }
}
