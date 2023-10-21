package com.management.lead.leadmangement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.management.lead.leadmangement.dto.LeadCaptureDTO;
import com.management.lead.leadmangement.dto.LeadContactDTO;
import com.management.lead.leadmangement.dto.LeadDTO;
import com.management.lead.leadmangement.entity.ActivityLog;
import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.LeadContactInfo;
import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.enumConstants.LeadPriority;
import com.management.lead.leadmangement.enumConstants.LeadStage;
import com.management.lead.leadmangement.repository.ActivityLogRepository;
import com.management.lead.leadmangement.repository.LeadCaptureRepository;
import com.management.lead.leadmangement.repository.LeadContactInfoRepository;
import com.management.lead.leadmangement.repository.LeadRepo;
import com.management.lead.leadmangement.repository.UserRepository;

import org.springframework.security.core.Authentication;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeadService {

    private LeadCaptureRepository leadCaptureRepository;
    private UserService userService;
    private LeadContactInfoRepository leadContactInfoRepository;
    private LeadRepo leadRepo;
    private ActivityLogRepository activityLogRepository;
    private UserRepository userRepository;
    private JavaMailSender emailSender;

    public LeadService(@Autowired LeadCaptureRepository leadCaptureRepository, UserService userService,
            LeadContactInfoRepository leadContactInfoRepository, LeadRepo leadRepo, JavaMailSender emailSender,
            ActivityLogRepository activityLogRepository, UserRepository userRepository) {
        this.leadCaptureRepository = leadCaptureRepository;
        this.userService = userService;
        this.leadContactInfoRepository = leadContactInfoRepository;
        this.leadRepo = leadRepo;
        this.activityLogRepository = activityLogRepository;
        this.emailSender = emailSender;
        this.userRepository = userRepository;
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

    public List<LeadContactInfo> getAllLeadContacts() {
        Iterable<LeadContactInfo> contactsIterable = leadContactInfoRepository.findAll();

        // Convert Iterable to List
        List<LeadContactInfo> contactsList = new ArrayList<>();
        contactsIterable.forEach(contactsList::add);

        return contactsList;
    }

    public List<User> getAllUsers() {
        Iterable<User> usersIterable = userRepository.findAll();

        // Convert Iterable to List
        List<User> usersList = new ArrayList<>();
        usersIterable.forEach(usersList::add);

        return usersList;
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

    public Lead soloPatch(Long leadId, LeadDTO leadDTO) {
        Lead existingLead = leadRepo.findById(leadId).orElse(null);

        if (existingLead == null) {
            return null;
        }

        // Update specific attributes of the lead entity from leadDTO
        if (leadDTO.getQuote() != null) {
            existingLead.setQuote(leadDTO.getQuote());
        }
        if (leadDTO.getBudget() != null) {
            existingLead.setBudget(leadDTO.getBudget());
        }
        if (leadDTO.getProbability() != null) {
            existingLead.setProbability(leadDTO.getProbability());
        }
        if (leadDTO.getPriority() != null) {
            existingLead.setPriority(LeadPriority.valueOf(leadDTO.getPriority()));
        }
        if (leadDTO.getStage() != null) {
            existingLead.setStage(LeadStage.valueOf(leadDTO.getStage()));
        }
        if (leadDTO.getSource() != null) {
            existingLead.setSource(leadDTO.getSource());
        }
        if (leadDTO.getTag() != null) {
            existingLead.setTag(leadDTO.getTag());
        }
        if (leadDTO.getExpectedClosingDate() != null) {
            existingLead.setExpectedClosingDate(leadDTO.getExpectedClosingDate());
        }

        if (leadDTO.getAssignedUser() != 0) {
            Optional<User> userOptional = userService.getById(leadDTO.getAssignedUser());
            User assignedUser = userOptional.orElse(null); // Return null if user is not found.
            existingLead.setAssignedUser(assignedUser);
        }

        // Save the updated lead
        return leadRepo.save(existingLead);
    }

    public void deleteLead(long leadId) {
        // Check if the opportunity exists
        if (!leadRepo.existsById(leadId)) {
            throw new EntityNotFoundException("Lead with ID " + leadId + " not found");
        }

        // Delete the opportunity
        leadRepo.deleteById(leadId);
    }

    public Lead getLeadById(Long leadId) {
        Optional<Lead> leadOptional = leadRepo.findById(leadId);

        if (leadOptional.isPresent()) {
            return leadOptional.get();
        } else {
            return null;
        }
    }

}
