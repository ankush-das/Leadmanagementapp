package com.management.lead.leadMangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadMangement.entity.LeadContactInfo;

@Repository
public interface LeadContactInfoRepository extends CrudRepository<LeadContactInfo, Long> {

}
