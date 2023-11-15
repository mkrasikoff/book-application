package com.mkrasikoff.bookservice.service;

import com.mkrasikoff.userservice.entity.User;
import com.mkrasikoff.userservice.producer.UserCreatedProducer;
import com.mkrasikoff.userservice.repo.UserRepository;
import com.mkrasikoff.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCreatedProducer userCreatedProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_whenUserExists_thenReturnsUser() {
        Long userId = 1L;
        User expectedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User foundUser = userService.get(userId);

        assertNotNull(foundUser);
        assertEquals(expectedUser, foundUser);
    }

    @Test
    void get_whenUserDoesNotExist_thenThrowsException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.get(userId));
    }

    @Test
    void getAll_whenCalled_thenReturnsAllUsers() {
        List<User> expectedUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> users = userService.getAll();

        assertEquals(expectedUsers.size(), users.size());
    }

    @Test
    void getActiveUser_whenActiveUserExists_thenReturnsActiveUser() {
        User activeUser = new User();
        activeUser.setActive(true);
        when(userRepository.findByIsActiveTrue()).thenReturn(Optional.of(activeUser));

        User foundUser = userService.getActiveUser();

        assertTrue(foundUser.isActive());
    }

    @Test
    void getActiveUser_whenNoActiveUser_thenThrowsException() {
        when(userRepository.findByIsActiveTrue()).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getActiveUser());
    }

    @Test
    void setActiveUser_whenUserExists_thenUpdatesActiveStatus() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.setActiveUser(userId, true);

        assertTrue(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void setActiveUser_whenUserDoesNotExist_thenThrowsException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.setActiveUser(userId, true));
    }

    @Test
    void existsById_whenUserExists_thenReturnsTrue() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        boolean exists = userService.existsById(userId);

        assertTrue(exists);
    }

    @Test
    void existsById_whenUserDoesNotExist_thenReturnsFalse() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        boolean exists = userService.existsById(userId);

        assertFalse(exists);
    }

    @Test
    void save_whenUserSaved_thenUserCreatedEventIsPublished() {
        User user = new User();
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.save(user);

        verify(userRepository, times(2)).save(user);
        verify(userCreatedProducer).sendUserId(user.getId());
        assertNotNull(savedUser);
    }

    @Test
    void update_whenUserExists_thenUpdatesUser() {
        Long userId = 1L;
        User existingUser = new User();
        User newUser = new User();
        newUser.setUsername("New Username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User updatedUser = userService.update(userId, newUser);

        assertEquals(newUser.getUsername(), updatedUser.getUsername());
    }

    @Test
    void update_whenUserDoesNotExist_thenThrowsException() {
        Long userId = 1L;
        User newUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.update(userId, newUser));
    }

    @Test
    void delete_whenUserExists_thenDeletesUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void delete_whenUserDoesNotExist_thenThrowsException() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> userService.delete(userId));
    }
}
