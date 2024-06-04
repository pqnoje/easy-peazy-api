package com.easy.service;

import java.util.List;

import com.easy.entity.Shelf;

public interface ShelfService {
	public Shelf create(Shelf shelf);

	public Shelf update(Shelf shelf, Long id);

	public List<Shelf> listAll();

	public void delete(Long id);

	public Shelf getById(Long id);
}
