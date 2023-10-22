package com.management.lead.leadmangement.controller;

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

import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.services.PipelineService;

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
    public ResponseEntity<Lead> nextLeadStageTransition(@PathVariable Long leadId) {
        Lead lead = pipelineService.nextstageChange(leadId);
        return ResponseEntity.ok(lead);

    }

    // Change to previous Stage
    @PutMapping("/transition/prev/{leadId}")
    public ResponseEntity<Lead> prevLeadStageTransition(@PathVariable Long leadId) {
        Lead lead = pipelineService.previousStageChange(leadId);
        return ResponseEntity.ok(lead);
    }

    // directly Closed Lost from any stage
    @PutMapping("/transition/lost/{leadId}")
    public ResponseEntity<Lead> lostLeadStageTransition(@PathVariable Long leadId) {
        Lead lead = pipelineService.lostStageChange(leadId);
        return ResponseEntity.ok(lead);
    }

    @PutMapping("/transition/closedwon/{leadId}")
    public ResponseEntity<Lead> closedWONLeadStageTransition(@PathVariable Long leadId) {
        Lead lead = pipelineService.closedWONStageChange(leadId); // Assuming a similar method in your service
        return ResponseEntity.ok(lead);
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
