package com.management.lead.leadMangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadMangement.entity.ActivityLog;

@Repository
public interface ActivityLogRepository extends CrudRepository<ActivityLog, Long> {

}
