package com.management.lead.leadmangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadmangement.entity.LeadContactInfo;

@Repository
public interface LeadContactInfoRepository extends CrudRepository<LeadContactInfo, Long> {

}
