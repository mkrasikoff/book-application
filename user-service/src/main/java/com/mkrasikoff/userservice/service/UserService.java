package com.mkrasikoff.userservice.service;

import com.mkrasikoff.userservice.entity.User;

public interface UserService {

    User save(User user);

    User get(Long id);

    void delete(Long id);

    User update(Long id, User newUser);
}
