package com.easy.service;

import java.util.List;

import com.easy.entity.Basket;
import com.easy.entity.Shelf;

public interface BasketService {
	public Basket create(Basket basket);

	public Basket update(Basket basket, Long id);

	public List<Basket> listAll();

	public void delete(Long id);

	public Basket getById(Long id);
}
