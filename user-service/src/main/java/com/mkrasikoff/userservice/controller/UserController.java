package com.mkrasikoff.userservice.controller;

import com.mkrasikoff.userservice.entity.User;
import com.mkrasikoff.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Conflict: Username or email already exists.");
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.update(id, user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getActiveUser() {
        User user = userService.getActiveUser();
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("No active user.", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/active/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> setActiveUser(@PathVariable Long id) {
        User user = userService.get(id);
        if (user != null) {
            userService.setActiveUser(id, true);
            return new ResponseEntity<>("Active user set.", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/validate/{id}")
    public ResponseEntity<?> validateUser(@PathVariable Long id) {
        if (userService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
