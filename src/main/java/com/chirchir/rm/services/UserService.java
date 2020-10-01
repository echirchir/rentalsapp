package com.chirchir.rm.services;

import com.chirchir.rm.models.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
