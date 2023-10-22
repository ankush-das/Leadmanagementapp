package com.management.lead.leadmangement.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.lead.leadmangement.entity.ActivityLog;
import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.User;

@Repository
public interface ActivityLogRepository extends CrudRepository<ActivityLog, Long> {
    @Query("SELECT log FROM ActivityLog log " +
            "WHERE log.user = :user AND log.lead = :lead")
    // @Query("SELECT details FROM activity_log log WHERE log.user_id = :user AND
    // log.lead_id = :lead")
    List<ActivityLog> findAllByUserAndLead(@Param("user") User user, @Param("lead") LeadCapture lead);
}
