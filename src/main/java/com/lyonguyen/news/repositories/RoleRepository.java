package com.lyonguyen.news.repositories;

import com.lyonguyen.news.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
