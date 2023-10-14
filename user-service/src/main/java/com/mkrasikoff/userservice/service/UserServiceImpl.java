package com.mkrasikoff.userservice.service;

import com.mkrasikoff.userservice.entity.User;
import com.mkrasikoff.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);

        userCreatedProducer.sendUserId(savedUser.getId());

        return userRepository.save(savedUser);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User update(Long id, User newUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            user.setEmail(newUser.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        Iterable<User> users = userRepository.findAll();
        users.forEach(userList::add);

        return userList;
    }

    @Override
    public User getActiveUser() {
        return userRepository.findByIsActiveTrue()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No active user"));
    }

    @Override
    public void setActiveUser(Long id, boolean isActive) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setActive(isActive);
        userRepository.save(user);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
