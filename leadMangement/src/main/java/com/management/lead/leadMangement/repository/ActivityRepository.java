package com.management.lead.leadMangement.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadMangement.entity.LeadActivity;
import com.management.lead.leadMangement.entity.LeadCapture;
import com.management.lead.leadMangement.entity.User;

@Repository
public interface ActivityRepository extends CrudRepository<LeadActivity, Integer> {

    @Query("SELECT la FROM LeadActivity la WHERE la.assignedUser = :user AND la.lead = :lead")
    List<LeadActivity> findActivitiesByAssignedUserAndLead(User user, LeadCapture lead);
}
