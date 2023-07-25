package com.mkrasikoff.userservice.service;

import com.mkrasikoff.userservice.entity.User;
import com.mkrasikoff.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(String username) {
        User user = new User();
        user.setUsername(username);
        user.setAmountOfBooks(0L);

        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User update(Long id, String username) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUsername(username);
        updatedUser.setAmountOfBooks(oldUser.getAmountOfBooks());

        return userRepository.save(updatedUser);
    }

    @Override
    public User updateBooks(Long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUsername(oldUser.getUsername());
        updatedUser.setAmountOfBooks(oldUser.getAmountOfBooks() + 1);

        return userRepository.save(updatedUser);
    }
}
