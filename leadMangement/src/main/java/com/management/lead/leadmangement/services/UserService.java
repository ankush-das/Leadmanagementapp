package com.management.lead.leadmangement.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.exception.UserNotFountException;
import com.management.lead.leadmangement.repository.UserRepository;

@Service
public class UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // User authentication logic
    public Optional<User> authenticate(String username, String password) {

        Optional<User> optUser = userRepository.findByName(username);
        if (optUser.isEmpty()) {
            throw new UserNotFountException("User not found");
        }

        if (!optUser.get().getPassword().equals(password)) {
            return Optional.empty();
        }
        return optUser;
    }

    public User create(User user) {
        user.setPassword("{bcrypt}" + passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    public Boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    public Optional<User> getByName(String name) {
        return userRepository.findByName(name);
    }
}
