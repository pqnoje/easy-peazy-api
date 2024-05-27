package com.ekan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekan.entity.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    Optional<Beneficiary> findById(Long id);
    List<Beneficiary> findAll();
}

