package com.ekan.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Beneficiary {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Nome do beneficiário.", example = "Jefferson Fernandes de Lucena")
    private String name;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Telefone do beneficiário.", example = "+55 (011) 9 95076-1002")
	@Column(nullable = false)
    private String phone;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Data de nascimento do beneficiário.", example = "1990-06-22")
	@Column(nullable = false)
    private Date birthdate;
	
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
    private Date created;
	
	@Column(nullable = false)
	@UpdateTimestamp
    private Date updated;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "beneficiary", 
    		fetch = FetchType.LAZY, 
    		cascade = {CascadeType.ALL})
    private List<Document> documents = new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
