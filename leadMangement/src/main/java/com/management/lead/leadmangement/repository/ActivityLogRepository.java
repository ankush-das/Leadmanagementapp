package com.management.lead.leadmangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadmangement.entity.ActivityLog;

@Repository
public interface ActivityLogRepository extends CrudRepository<ActivityLog, Long> {

}
