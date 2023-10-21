package com.management.lead.leadmangement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadmangement.entity.Lead;
import com.management.lead.leadmangement.enumConstants.LeadStage;

@Repository
public interface LeadRepo extends JpaRepository<Lead, Long> {
    List<Lead> findByStage(LeadStage stage);

    List<Lead> findAll();

    List<Lead> findByLeadContactCompanyName(String companyName);

    List<Lead> findBySource(String source);
}
