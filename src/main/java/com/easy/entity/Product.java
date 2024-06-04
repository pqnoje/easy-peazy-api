package com.easy.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Tipo de documento do beneficiário.", example = "Tipo sanguíneo")
	@Column(nullable = false)
	private String type;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Informações relevantes ao tipo do documento do beneficiário.", example = "O-")
	@Column(nullable = false)
	private String description;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "O preço do produto para a venda final.", example = "9.99")
	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	@CreationTimestamp
	private Date created;

	@Column(nullable = false)
	@UpdateTimestamp
	private Date updated;

	@JsonBackReference(value = "product-shelf")
	@ManyToMany
	@JoinTable(name = "shelf_product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "shelf_id"))
	private List<Shelf> shelfs = new ArrayList<>();

	@JsonBackReference(value = "basket-product")
	@ManyToOne
	private Basket basket;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public List<Shelf> getShelfs() {
		return shelfs;
	}

	public void setShelfs(List<Shelf> shelfs) {
		this.shelfs = shelfs;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
