package com.document.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.document.dao.DocDao;
import com.document.entity.Document;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DocService {

	private final DocDao dao;

	public DocService(DocDao dao) {

		this.dao = dao;
	}

	public Document saveDocument(Document document) {
		return dao.save(document);
	}

	public Document getDocumentById(String id) {
		return dao.findById(id).orElse(null);

	}

	public List<Document> getAllDocument() {
		return dao.findAll();

	}

	public boolean deleteDocument(String id) {
		dao.deleteById(id);
		return false;
	}
}
