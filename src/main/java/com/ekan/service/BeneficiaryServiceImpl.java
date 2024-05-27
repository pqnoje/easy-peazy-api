package com.ekan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekan.entity.Beneficiary;
import com.ekan.repository.BeneficiaryRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Autowired
    BeneficiaryRepository beneficiaryReposisory;
    	
    @Transactional
	public Beneficiary create(Beneficiary newBeneficiary) {
		try {
			Beneficiary beneficiary = this.beneficiaryReposisory.save(newBeneficiary);
			return beneficiary;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("INVALID");
		}
	}
    
    @Transactional
	public Beneficiary update(Beneficiary updateBeneficiary, Long id) {
    	try {
    		updateBeneficiary.setId(id);
    		Optional<Beneficiary> optionalBeneficiary =  this.beneficiaryReposisory.findById(id);
    		Beneficiary beneficiary = optionalBeneficiary.get();
    		updateBeneficiary.setCreated(beneficiary.getCreated());
        	return this.beneficiaryReposisory.save(updateBeneficiary);

    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new IllegalArgumentException("INVALID");
    	}
	}
	
    @Transactional
    public List<Beneficiary> listAll() {
		return this.beneficiaryReposisory.findAll();
	}
    
    @Transactional
    public void delete(Long id) {
    	Optional<Beneficiary> beneficiary = this.beneficiaryReposisory.findById(id);
    	if (beneficiary.isPresent()) {
    		this.beneficiaryReposisory.delete(beneficiary.get());
    	} else {
    		throw new IllegalArgumentException("INVALID");
    	}
	}
}
