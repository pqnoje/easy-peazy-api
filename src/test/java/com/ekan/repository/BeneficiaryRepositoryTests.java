package com.ekan.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ekan.entity.Beneficiary;
import com.ekan.entity.Document;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeneficiaryRepositoryTests {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Test
    @DisplayName("Test 1:Save Beneficiary Test")
    @Order(1)
    @Rollback(value = false)
    public void saveBeneficiaryTest(){
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setName("Jeff");
        beneficiary.setBirthdate(new Date());
        beneficiary.setPhone("11950761002");
        Document document = new Document();
        document.setType("Tipo Sanguíneo");
        document.setDescription("O-");
        beneficiary.getDocuments().add(document);
        
        beneficiaryRepository.save(beneficiary);

        Assertions.assertThat(beneficiary.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getBeneficiaryTest(){
    	Beneficiary beneficiary = beneficiaryRepository.findById(1L).get();
        Assertions.assertThat(beneficiary.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfBeneficiariesTest(){
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        Assertions.assertThat(beneficiaries.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateBeneficiaryTest(){
        Beneficiary beneficiary = beneficiaryRepository.findById(1L).get();
        beneficiary.setPhone("+55 11 9 5076-5050");
        Beneficiary beneficiaryUpdated =  beneficiaryRepository.save(beneficiary);
        System.out.println(beneficiaryUpdated);
        Assertions.assertThat(beneficiaryUpdated.getPhone()).isEqualTo("+55 11 9 5076-5050");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteBeneficiaryTest(){
        beneficiaryRepository.deleteById(1L);
        Optional<Beneficiary> beneficiaryOptional = beneficiaryRepository.findById(1L);
        Assertions.assertThat(beneficiaryOptional).isEmpty();
    }

}