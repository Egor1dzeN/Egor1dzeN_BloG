package com.boots.repository;

import com.boots.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findUserById(Long id);
    List<User> findByUsernameContainingIgnoreCase(String username);
}
