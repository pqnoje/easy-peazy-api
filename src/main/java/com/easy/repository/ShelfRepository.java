package com.easy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easy.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
	Optional<Shelf> findById(Long id);

	List<Shelf> findAll();
}
