package com.ekan.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Document {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Tipo de documento do beneficiário.", example = "Tipo sanguíneo")
	@Column(nullable = false)
	private String type;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Informações relevantes ao tipo do documento do beneficiário.", example = "O-")
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	@CreationTimestamp
	private Date created;
	
	@Column(nullable = false)
	@UpdateTimestamp
	private Date updated;
	
	@JsonBackReference
	@ManyToOne
	private Beneficiary beneficiary;
	
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
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}
}
