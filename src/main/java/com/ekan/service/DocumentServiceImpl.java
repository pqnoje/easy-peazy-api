package com.ekan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekan.entity.Document;
import com.ekan.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
	DocumentRepository documentReposisory;
	
    @Transactional
    public List<Document> listBeneficiaryDocuments(Long id) {
    	return this.documentReposisory.findByBeneficiaryId(id);
	}

}
