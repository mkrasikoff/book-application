package com.epam.postingservice.service;

import com.epam.postingservice.entity.User;

public interface UserService {

    User save(String username);

    User get(Long id);

    void delete(Long id);

    User update(Long id, String username);

    User iterateNumberOfPosts(Long id);
}
