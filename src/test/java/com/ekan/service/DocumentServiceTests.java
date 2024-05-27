package com.ekan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

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
import com.ekan.repository.DocumentRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DocumentServiceTests {

	@Mock
	private DocumentRepository documentRepository;

	@InjectMocks
	private DocumentServiceImpl documentService;

	private Document document;
	
	@BeforeEach
	public void setup(){
		document = new Document();
		document.setType("Sangue");
		document.setDescription("O-");
	}

	@Test
	@Order(1)
	public void getDocumentsByBeneficiary(){
		Document document1 = new Document();
		document1.setType("Sangue");
		document1.setDescription("O-");

		given(documentRepository.findByBeneficiaryId(1L)).willReturn(List.of(document, document1));

		List<Document> documentList = documentService.listBeneficiaryDocuments(1L);

		assertThat(documentList).isNotNull();
		assertThat(documentList.size()).isGreaterThan(1);
	}
}