package com.management.lead.leadMangement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.lead.leadMangement.entity.Lead;
import com.management.lead.leadMangement.exception.InvalidStateException;
import com.management.lead.leadMangement.services.PipelineService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("/api/lead")
public class PipelineController {
    private PipelineService pipelineService;

    public PipelineController(@Autowired PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    // Mapping to get all opportunities
    @GetMapping("/all")
    public ResponseEntity<List<Lead>> getAllLeads() {
        List<Lead> opportunities = pipelineService.findAll();
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    // Mapping to get opportunities in the "New" stage
    @GetMapping("/new")
    public ResponseEntity<List<Lead>> getNewLeads() {
        List<Lead> opportunities = pipelineService.findNewOpportunities();
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    // Mapping to get opportunities in the "Qualified" stage
    @GetMapping("/qualified")
    public ResponseEntity<List<Lead>> getQualifiedLeads() {
        List<Lead> opportunities = pipelineService.findQualifiedOpportunities();
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    // Mapping to get opportunities in the "Proposition" stage
    @GetMapping("/proposition")
    public ResponseEntity<List<Lead>> getPropositionLeads() {
        List<Lead> opportunities = pipelineService.findPropositionOpportunities();
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    // Mapping to get opportunities in the "Negotiation" stage
    @GetMapping("/negotiation")
    public ResponseEntity<List<Lead>> getNegotiationLeads() {
        List<Lead> opportunities = pipelineService.findNegotiationOpportunities();
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    // Mapping to get opportunities in the "Won" stage
    @GetMapping("/won")
    public ResponseEntity<List<Lead>> getWonLeads() {
        List<Lead> opportunities = pipelineService.findWonOpportunities();
        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    // Change to next Stage
    @PutMapping("/transition/next/{leadId}")
    public ResponseEntity<?> nextLeadStageTransition(@PathVariable Long leadId) {
        try {
            Lead lead = pipelineService.nextstageChange(leadId);
            return ResponseEntity.ok(lead);
        } catch (InvalidStateException e) {
            return ResponseEntity.badRequest().body("Invalid state transition.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Change to previous Stage
    @PutMapping("/transition/prev/{leadId}")
    public ResponseEntity<?> prevLeadStageTransition(@PathVariable Long leadId) {
        try {
            Lead lead = pipelineService.previousStageChange(leadId);
            return ResponseEntity.ok(lead);
        } catch (InvalidStateException e) {
            return ResponseEntity.badRequest().body("Invalid state transition.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // directly Closed Lost from any stage
    @PutMapping("/transition/lost/{leadId}")
    public ResponseEntity<?> lostLeadStageTransition(@PathVariable Long leadId) {
        try {
            Lead lead = pipelineService.lostStageChange(leadId);
            return ResponseEntity.ok(lead);
        } catch (InvalidStateException e) {
            return ResponseEntity.badRequest().body("Invalid state transition.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // search by company
    @GetMapping("/search/company")
    public List<Lead> searchLeadsByCompanyName(@RequestParam String companyname) {
        return pipelineService.searchLeadsByCompanyName(companyname);
    }

    // search by source
    @GetMapping("/search/source")
    public List<Lead> searchLeadsBySource(@RequestParam String source) {
        return pipelineService.searchLeadsBySource(source);
    }
}
