package com.management.lead.leadmangement.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadmangement.entity.LeadActivity;
import com.management.lead.leadmangement.entity.LeadCapture;
import com.management.lead.leadmangement.entity.User;

@Repository
public interface ActivityRepository extends CrudRepository<LeadActivity, Integer> {

    @Query("SELECT la FROM LeadActivity la WHERE la.assignedUser = :user AND la.lead = :lead")
    List<LeadActivity> findActivitiesByAssignedUserAndLead(User user, LeadCapture lead);
}
