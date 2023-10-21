package com.management.lead.leadmangement.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.lead.leadmangement.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findByName(String name);

    Boolean existsByName(String name);
}
