package com.mkrasikoff.userservice.controller;

import com.mkrasikoff.userservice.dto.UserDTO;
import com.mkrasikoff.userservice.entity.User;
import com.mkrasikoff.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User save(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        return userService.save(username);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        return userService.update(id, username);
    }

    @PatchMapping("/{id}/books")
    public User updateBooks(@PathVariable Long id) {
        return userService.updateBooks(id);
    }
}
