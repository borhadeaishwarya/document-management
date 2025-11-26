package com.document.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.entity.Document;
import com.document.service.DocService;

@RestController
@RequestMapping("/api/document")
public class DocController {

	private final DocService service;

	public DocController(DocService service) {
		this.service = service;
	}

	@PostMapping
	public Document createDocument(@RequestBody Document document) {
		return service.saveDocument(document);
	}

	@GetMapping
	public List<Document> getAllDoument() {
		return service.getAllDocument();
	}

	@GetMapping("/{id}")
	public Document getDocumentById(@PathVariable String id) {
		return service.getDocumentById(id);
	}

	@PutMapping("/{id}")
	public Document updateDocument(@PathVariable String id, @RequestBody Document update) {
		Document old = service.getDocumentById(id);

		if (old != null) {
			old.setDocId(update.getDocId());
			old.setDocTitle(update.getDocTitle());
			old.setFileName(update.getFileName());
			old.setPath(update.getPath());
			old.setVersion(update.getVersion());
			old.setDocClassName(update.getDocClassName());

			return service.saveDocument(update);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public String deleteDocument(@PathVariable String id) {
		if (service.deleteDocument(id)) {
			service.deleteDocument(id);
			return "document delete successfully " + id;

		}
		return "Document not found : " + id;

	}

	@GetMapping("/welcome")
	public String getMsg() {
		return "welcome postman";
	}
}
