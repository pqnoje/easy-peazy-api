package com.easy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.entity.Basket;
import com.easy.repository.BasketRepository;

@Service
public class BasketServiceImpl implements BasketService {
	@Autowired
	BasketRepository basketReposisory;

	@Transactional
	public Basket create(Basket newBasket) {
		try {
			Basket basket = this.basketReposisory.save(newBasket);
			return basket;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}

	@Transactional
	public Basket update(Basket updateBasket, Long id) {
		try {
			updateBasket.setId(id);
			Optional<Basket> optionalBasket = this.basketReposisory.findById(id);
			Basket basket = optionalBasket.get();
			updateBasket.setCreated(basket.getCreated());
			return this.basketReposisory.save(updateBasket);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}

	@Transactional
	public Basket getById(Long id) {
		Optional<Basket> basket = this.basketReposisory.findById(id);
		if (!basket.isPresent()) {
			throw new IllegalArgumentException("NOT FOUND");
		}
		return basket.get();
	}

	@Transactional
	public List<Basket> listAll() {
		return this.basketReposisory.findAll();
	}

	@Transactional
	public void delete(Long id) {
		Optional<Basket> basket = this.basketReposisory.findById(id);
		if (basket.isPresent()) {
			this.basketReposisory.delete(basket.get());
		} else {
			throw new IllegalArgumentException("INVALID");
		}
	}
}
