package com.management.lead.leadMangement.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.management.lead.leadmangement.entity.User;
import com.management.lead.leadmangement.exception.UserNotFountException;
import com.management.lead.leadmangement.repository.UserRepository;
import com.management.lead.leadmangement.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
    }

    @Test
    void testAuthenticate_UserFound() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setName(username);
        user.setPassword("{bcrypt}$2a$10$2YCrL1LAXFwDYDU/n/6fLO6lKqfOfAAlXKXUuPtgA2ebJwsQ8Gta."); // A bcrypt-hashed
                                                                                                  // password

        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        Optional<User> authenticatedUser = userService.authenticate(username, password);

        assertTrue(authenticatedUser.isPresent());
        assertEquals(username, authenticatedUser.get().getName());
    }

    @Test
    void testAuthenticate_UserNotFound() {
        String username = "nonExistentUser";
        String password = "testPassword";

        when(userRepository.findByName(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFountException.class, () -> userService.authenticate(username, password));
    }

    @Test
    void testAuthenticate_InvalidPassword() {
        String username = "testUser";
        String password = "invalidPassword";
        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode("correctPassword"));

        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        Optional<User> authenticatedUser = userService.authenticate(username, password);

        assertTrue(authenticatedUser.isEmpty());
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("newUser");
        user.setPassword("password");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assertEquals(user.getName(), createdUser.getName());
        assertTrue(createdUser.getPassword().startsWith("{bcrypt}"));
        assertTrue(passwordEncoder.matches("password", createdUser.getPassword().substring(8))); // Remove "{bcrypt}"
    }

    @Test
    void testGetUserById() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.getById(userId);

        assertTrue(retrievedUser.isPresent());
        assertEquals(userId, retrievedUser.get().getId());
    }

    @Test
    void testExistsByName_Exists() {
        String username = "existingUser";
        when(userRepository.existsByName(username)).thenReturn(true);

        boolean exists = userService.existsByName(username);

        assertTrue(exists);
    }

    @Test
    void testExistsByName_NotExists() {
        String username = "nonExistentUser";
        when(userRepository.existsByName(username)).thenReturn(false);

        boolean exists = userService.existsByName(username);

        assertFalse(exists);
    }

    @Test
    void testGetByName() {
        String username = "testUser";
        User user = new User();
        user.setName(username);

        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.getByName(username);

        assertTrue(retrievedUser.isPresent());
        assertEquals(username, retrievedUser.get().getName());
    }

}
