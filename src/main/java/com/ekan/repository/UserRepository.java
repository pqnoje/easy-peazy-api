package com.ekan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekan.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

