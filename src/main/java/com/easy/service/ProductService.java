package com.easy.service;

import java.util.List;

import com.easy.entity.Product;
import com.easy.entity.Shelf;

public interface ProductService {
	public Product create(Product product);

	public Product update(Product product, Long id);

	public List<Product> listAll();

	public void delete(Long id);

	public List<Product> listAllByShelf(Long id);

	public List<Product> listAllByBasket(Long id);

	public Product getById(Long id);
}
