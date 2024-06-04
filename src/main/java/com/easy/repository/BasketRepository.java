package com.easy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easy.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
	Optional<Basket> findById(Long id);

	List<Basket> findAll();
}
