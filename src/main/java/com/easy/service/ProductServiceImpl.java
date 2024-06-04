package com.easy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.entity.Product;
import com.easy.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productReposisory;

	@Transactional
	public Product create(Product newProduct) {
		try {
			Product product = this.productReposisory.save(newProduct);
			return product;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}

	@Transactional
	public Product update(Product updateProduct, Long id) {
		try {
			updateProduct.setId(id);
			Optional<Product> optionalProduct = this.productReposisory.findById(id);
			Product product = optionalProduct.get();
			updateProduct.setCreated(product.getCreated());
			return this.productReposisory.save(updateProduct);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}

	@Transactional
	public Product getById(Long id) {
		Optional<Product> product = this.productReposisory.findById(id);
		if (!product.isPresent()) {
			throw new IllegalArgumentException("NOT FOUND");
		}
		return product.get();
	}

	@Transactional
	public List<Product> listAll() {
		return this.productReposisory.findAll();
	}

	@Transactional
	public List<Product> listAllByShelf(Long id) {
		return this.productReposisory.findByShelfs_Id(id);
	}

	@Transactional
	public List<Product> listAllByBasket(Long id) {
		return this.productReposisory.findByBasketId(id);
	}

	@Transactional
	public void delete(Long id) {
		Optional<Product> product = this.productReposisory.findById(id);
		if (product.isPresent()) {
			this.productReposisory.delete(product.get());
		} else {
			throw new IllegalArgumentException("INVALID");
		}
	}
}
