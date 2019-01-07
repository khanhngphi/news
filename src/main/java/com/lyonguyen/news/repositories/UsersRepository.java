package com.lyonguyen.news.repositories;

import com.lyonguyen.news.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
