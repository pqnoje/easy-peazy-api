package com.easy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easy.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
