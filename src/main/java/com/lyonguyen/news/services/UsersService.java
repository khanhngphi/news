package com.lyonguyen.news.services;

import com.lyonguyen.news.models.User;

public interface UsersService {

    void save(User user);

    User findByUsername(String username);
}
