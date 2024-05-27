package com.ekan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekan.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findById(Long id);
    List<Document> findByBeneficiaryId(Long id);
    List<Document> findAll();
}

