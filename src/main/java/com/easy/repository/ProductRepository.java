package com.easy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easy.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findById(Long id);

	List<Product> findByShelfs_Id(Long id);

	List<Product> findByBasketId(Long id);

	List<Product> findAll();
}
