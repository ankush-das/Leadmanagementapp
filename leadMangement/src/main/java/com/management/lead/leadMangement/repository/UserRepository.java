package com.management.lead.leadMangement.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadMangement.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findByName(String name);

    Boolean existsByName(String name);
}
