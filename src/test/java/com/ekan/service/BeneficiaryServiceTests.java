package com.ekan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ekan.entity.Beneficiary;
import com.ekan.entity.Document;
import com.ekan.repository.BeneficiaryRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeneficiaryServiceTests {

	@Mock
	private BeneficiaryRepository beneficiaryRepository;

	@InjectMocks
	private BeneficiaryServiceImpl beneficiaryService;

	private Beneficiary beneficiary;

	private Document document;

	@BeforeEach
	public void setup(){
		beneficiary = new Beneficiary();
		document = new Document();
		beneficiary.setId(1L);
		beneficiary.setName("Jefferson");
		beneficiary.setPhone("11950761002");  
		document.setType("Sangue");
		document.setDescription("O-");
		beneficiary.getDocuments().add(document);;
	}

	@Test
	@Order(1)
	public void saveBeneficiary(){
		given(beneficiaryRepository.save(beneficiary)).willReturn(beneficiary);
		Beneficiary savedBeneficiary = beneficiaryService.create(beneficiary);
		assertThat(savedBeneficiary).isNotNull();
	}


	@Test
	@Order(3)
	public void getAllBeneficiary(){
		Beneficiary beneficiary1 = new Beneficiary();
		beneficiary1 = new Beneficiary();
		beneficiary1.setId(1L);
		beneficiary1.setName("Jefferson");
		beneficiary1.setPhone("11950761002");  

		given(beneficiaryRepository.findAll()).willReturn(List.of(beneficiary, beneficiary1));

		List<Beneficiary> beneficiaryList = beneficiaryService.listAll();

		assertThat(beneficiaryList).isNotNull();
		assertThat(beneficiaryList.size()).isGreaterThan(1);
	}

	@Test
	@Order(4)
	public void updateBeneficiary(){
		given(beneficiaryRepository.findById(beneficiary.getId())).willReturn(Optional.of(beneficiary));
		beneficiary.setName("Paulo");
		beneficiary.setPhone("+55 (011) 9 95076-1002");
		given(beneficiaryRepository.save(beneficiary)).willReturn(beneficiary);

		Beneficiary updatedBeneficiary = beneficiaryService.update(beneficiary, beneficiary.getId());

		assertThat(updatedBeneficiary.getName()).isEqualTo("Paulo");
		assertThat(updatedBeneficiary.getPhone()).isEqualTo("+55 (011) 9 95076-1002");
	}
}