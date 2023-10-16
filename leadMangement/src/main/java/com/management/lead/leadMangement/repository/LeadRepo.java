package com.management.lead.leadMangement.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.lead.leadMangement.entity.Lead;
import com.management.lead.leadMangement.entity.User;
import com.management.lead.leadMangement.enumConstants.LeadStage;

@Repository
public interface LeadRepo extends JpaRepository<Lead, Long> {
    List<Lead> findByStage(LeadStage stage);

    List<Lead> findAll();

    List<Lead> findByLeadContactCompanyName(String companyName);

    List<Lead> findBySource(String source);
}
