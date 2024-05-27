package com.ekan.service;

import java.util.List;

import com.ekan.entity.Beneficiary;

public interface BeneficiaryService {
	public Beneficiary create(Beneficiary newBeneficiary);
	public Beneficiary update(Beneficiary updateBeneficiary, Long id);
    public List<Beneficiary> listAll();
	public void delete(Long id);
}
