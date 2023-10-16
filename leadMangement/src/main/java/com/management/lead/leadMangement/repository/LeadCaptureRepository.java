package com.management.lead.leadMangement.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.lead.leadMangement.entity.LeadCapture;

@Repository
public interface LeadCaptureRepository extends CrudRepository<LeadCapture, Long> {
    public void deleteById(int id);

    @Query("SELECT lc.id FROM LeadCapture lc WHERE lc.email = :email")
    Long findIdByEmail(@Param("email") String email);
}
