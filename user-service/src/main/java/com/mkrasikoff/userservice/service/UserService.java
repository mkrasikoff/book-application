package com.mkrasikoff.userservice.service;

import com.mkrasikoff.userservice.entity.User;

public interface UserService {

    User save(String username);

    User get(Long id);

    void delete(Long id);

    User update(Long id, String username);

    User updateBooks(Long id);
}
