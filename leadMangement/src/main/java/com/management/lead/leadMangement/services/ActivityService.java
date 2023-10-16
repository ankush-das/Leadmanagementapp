package com.management.lead.leadMangement.services;

import java.util.Optional;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.management.lead.leadMangement.dto.ActivityDTO;
import com.management.lead.leadMangement.entity.ActivityLog;
import com.management.lead.leadMangement.entity.LeadActivity;
import com.management.lead.leadMangement.entity.LeadCapture;
import com.management.lead.leadMangement.entity.User;
import com.management.lead.leadMangement.enumConstants.ActivityStatus;
import com.management.lead.leadMangement.repository.ActivityLogRepository;
import com.management.lead.leadMangement.repository.ActivityRepository;
import com.management.lead.leadMangement.repository.LeadCaptureRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActivityService {

    private ActivityRepository activityRepository;
    private UserService userService;
    private LeadCaptureRepository leadCaptureRepository;
    private ActivityLogRepository activityLogRepository;

    public ActivityService(@Autowired ActivityRepository activityRepository, @Autowired UserService userService,
            @Autowired LeadCaptureRepository leadCaptureRepository,
            @Autowired ActivityLogRepository activityLogRepository) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.leadCaptureRepository = leadCaptureRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public LeadActivity createActivity(long capturedLeadId, ActivityDTO activityDTO) {

        // get assigned user
        Optional<User> userOptional = userService.getById(activityDTO.getAssignedUser());
        User assignedUser = userOptional.orElseThrow(() -> new EntityNotFoundException("User not found"));

        // get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> loggedinUser = userService.getByName(username);

        LeadCapture leadCaptureData = leadCaptureRepository.findById(capturedLeadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " + capturedLeadId + " not found"));

        LeadActivity leadActivity = new LeadActivity();
        leadActivity.setActivityStatus(ActivityStatus.valueOf(activityDTO.getActivityStatus()));
        leadActivity.setActivityType(activityDTO.getActivityType());
        leadActivity.setDetail(activityDTO.getDetail());
        leadActivity.setSummary(activityDTO.getSummary());
        leadActivity.setDueDate(activityDTO.getDueDate());
        leadActivity.setLead(leadCaptureData);
        leadActivity.setAssignedUser(assignedUser);
        leadActivity.setCreatedDate(new Date());

        if (loggedinUser.isPresent()) {
            leadActivity.setCreatedBy(loggedinUser.get());
        }

        // creating a activity log
        ActivityLog activityLog = new ActivityLog();
        activityLog.setLogDate(new Date());
        activityLog.setDetails("Performed activity: " + activityDTO.getActivityType());
        activityLog.setUser(userOptional.orElse(null)); // Set the user if available
        activityLog.setLead(leadCaptureData);

        // Save the ActivityLog
        activityLogRepository.save(activityLog);

        return activityRepository.save(leadActivity);
    }

    public List<LeadActivity> allActivity(long capturedLeadId) {
        // get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByName(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " +
                        username + " not found"));

        LeadCapture leadCaptureData = leadCaptureRepository.findById(capturedLeadId)
                .orElseThrow(() -> new EntityNotFoundException("Lead with ID " +
                        capturedLeadId + " not found"));

        // Retrieve all activities between the lead and the user
        return activityRepository.findActivitiesByAssignedUserAndLead(user, leadCaptureData);
    }
}
