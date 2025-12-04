package com.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.authRequest.AuthRequest;
import com.document.entity.Document;
import com.document.security.JwtSecure;
import com.document.service.DocService;
//http://localhost:9090/api/document/welcome
//http://localhost:9090/api/document/D101
@RestController
@RequestMapping("/api/document")
public class DocController {

    private final DocService service;
    private final JwtSecure jwtSecure;

    public DocController(DocService service, JwtSecure jwtSecure) {
        this.service = service;
        this.jwtSecure = jwtSecure;
    }

    // ✔ PUBLIC API (no JWT required)
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        if (request.getUsername().equals("aish") && request.getPassword().equals("1234")) {
            return jwtSecure.generateToken(request.getUsername());
        }
        return "Invalid username or password";
    }

    // ✔ SECURED
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

            return service.saveDocument(old);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable String id) {
        if (service.deleteDocument(id)) {
            return "Document deleted successfully: " + id;
        }
        return "Document not found: " + id;
    }

    // ✔ Public welcome
    @GetMapping("/welcome")
    public String getMsg() {
        return "welcome postman";
    }
}

