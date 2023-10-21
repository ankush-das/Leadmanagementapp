package com.management.lead.leadmangement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.lead.leadmangement.dto.LeadCaptureDTO;
import com.management.lead.leadmangement.dto.LeadContactDTO;
import com.management.lead.leadmangement.dto.LeadDTO;
import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.LeadContactInfo;
import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.services.LeadService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/leads")
public class LeadController {

    private LeadService leadService;

    public LeadController(@Autowired LeadService leadService) {
        this.leadService = leadService;
    }

    // Capture the lead
    @PostMapping("/create/capture")
    public ResponseEntity<LeadCapture> captureLead(@RequestBody LeadCaptureDTO leadCaptureDTO) {
        LeadCapture capturedLead = leadService.createCapturedLead(leadCaptureDTO);
        return ResponseEntity.ok(capturedLead);
    }

    // update the captured lead
    @PutMapping("/capture/update/{leadCaptureId}")
    public ResponseEntity<LeadCapture> updateLeadCapture(
            @PathVariable Long leadCaptureId,
            @RequestBody LeadCaptureDTO leadCaptureDTO) {
        try {
            LeadCapture updatedLeadCapture = leadService.updateCapturedLead(leadCaptureId, leadCaptureDTO);
            return new ResponseEntity<>(updatedLeadCapture, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete the captured need
    @DeleteMapping("/capture/delete/{leadCaptureId}")
    public ResponseEntity<Void> deleteLeadCapture(@PathVariable Long leadCaptureId) {
        try {
            leadService.deleteLeadCapture(leadCaptureId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // create Contact for the Captured lead
    @PostMapping("/contactInfo/{capturedId}")
    public ResponseEntity<LeadContactInfo> createLeadContact(
            @PathVariable("capturedId") int capturedId,
            @RequestBody LeadContactDTO leadContactDTO) {
        try {
            LeadContactInfo createdContactInfo = leadService.createLeadContactInfo(capturedId, leadContactDTO);
            return new ResponseEntity<>(createdContactInfo, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allcontacts")
    public List<LeadContactInfo> getAllLeadContacts() {
        return leadService.getAllLeadContacts();
    }

    @PutMapping("update/{contactInfoId}")
    public LeadContactInfo updateLeadContactInfo(
            @PathVariable long contactInfoId,
            @RequestBody LeadContactDTO leadContactDTO) {
        return leadService.updateLeadContactInfo(contactInfoId, leadContactDTO);
    }

    @DeleteMapping("/contact/delete/{contactInfoId}")
    public void deleteLeadContactInfo(@PathVariable long contactInfoId) {
        leadService.deleteLeadContactInfo(contactInfoId);
    }

    // create full lead
    @PostMapping("/create/{leadId}")
    public ResponseEntity<Lead> createLead(
            @PathVariable("leadId") int leadId,
            @RequestBody LeadDTO leadDTO) {
        try {
            Lead lead = leadService.createLead(leadId, leadDTO);
            return new ResponseEntity<>(lead, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update lead
    @PostMapping("/lead/update/{leadId}")
    public ResponseEntity<Lead> updateLead(
            @PathVariable("leadId") int leadId,
            @RequestBody LeadDTO leadDTO) {
        try {
            Lead updatedLead = leadService.updateLead(leadId,
                    leadDTO);
            return new ResponseEntity<>(updatedLead, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/lead/patch/{leadId}")
    public ResponseEntity<Lead> updateLead(@PathVariable Long leadId, @RequestBody LeadDTO leadDTO) {
        Lead updatedLead = leadService.soloPatch(leadId, leadDTO);

        if (updatedLead != null) {
            // Successfully updated the Lead.
            return ResponseEntity.ok(updatedLead);
        } else {
            // Lead with the specified ID not found.
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allusers")
    public List<User> getUsers() {
        return leadService.getAllUsers();
    }

    // not working so need to test it
    @DeleteMapping("/lead/delete/{leadId}")
    public ResponseEntity<Void> deleteLead(
            @PathVariable("leadId") long leadId) {
        try {
            leadService.deleteLead(leadId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content on successful deletion
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<Lead> leadDetail(@PathVariable Long leadId) {
        Lead lead = leadService.getLeadById(leadId);

        if (lead != null) {
            return ResponseEntity.ok(lead);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
