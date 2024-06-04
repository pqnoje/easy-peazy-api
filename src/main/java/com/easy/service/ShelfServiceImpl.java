package com.easy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.entity.Shelf;
import com.easy.repository.ShelfRepository;

@Service
public class ShelfServiceImpl implements ShelfService {
	@Autowired
	ShelfRepository shelfReposisory;

	@Transactional
	public Shelf create(Shelf newShelf) {
		try {
			Shelf shelf = this.shelfReposisory.save(newShelf);
			return shelf;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}

	@Transactional
	public Shelf update(Shelf updateShelf, Long id) {
		try {
			updateShelf.setId(id);
			Optional<Shelf> optionalShelf = this.shelfReposisory.findById(id);
			Shelf shelf = optionalShelf.get();
			updateShelf.setCreated(shelf.getCreated());
			return this.shelfReposisory.save(updateShelf);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}

	@Transactional
	public Shelf getById(Long id) {
		Optional<Shelf> shelf = this.shelfReposisory.findById(id);
		if (!shelf.isPresent()) {
			throw new IllegalArgumentException("NOT FOUND");
		}
		return shelf.get();
	}

	@Transactional
	public List<Shelf> listAll() {
		return this.shelfReposisory.findAll();
	}

	@Transactional
	public void delete(Long id) {
		Optional<Shelf> shelf = this.shelfReposisory.findById(id);
		if (shelf.isPresent()) {
			this.shelfReposisory.delete(shelf.get());
		} else {
			throw new IllegalArgumentException("INVALID");
		}
	}
}
