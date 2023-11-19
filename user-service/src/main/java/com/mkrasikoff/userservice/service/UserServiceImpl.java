package com.mkrasikoff.userservice.service;

import com.mkrasikoff.userservice.entity.User;
import com.mkrasikoff.userservice.producer.UserCreatedProducer;
import com.mkrasikoff.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCreatedProducer userCreatedProducer;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);

        userCreatedProducer.sendUserId(savedUser.getId());

        return userRepository.save(savedUser);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
                    log.error("User with ID '{}' not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            log.error("Attempt to delete non-existent user with ID '{}'", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id, User newUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            user.setEmail(newUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> {
            log.error("Failed to update user with ID '{}': Not Found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        userRepository.findAll().forEach(userList::add);

        return userList;
    }

    @Override
    public User getActiveUser() {
        return userRepository.findByIsActiveTrue()
                .orElseThrow(() -> {
                    log.error("No active user found");
                    return new ResponseStatusException(HttpStatus.NO_CONTENT, "No active user");
                });
    }

    @Override
    public void setActiveUser(Long id, boolean isActive) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with ID '{}' not found when trying to set active status", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        user.setActive(isActive);
        userRepository.save(user);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
