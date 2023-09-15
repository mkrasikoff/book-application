package com.mkrasikoff.userservice.service;

import com.mkrasikoff.userservice.entity.User;
import java.util.List;

public interface UserService {

    User save(User user);

    User get(Long id);

    void delete(Long id);

    User update(Long id, User newUser);

    List<User> getAll();

    User getActiveUser();

    void setActiveUser(Long id, boolean isActive);

    boolean existsById(Long id);
}
