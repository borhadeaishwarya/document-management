	package com.document.controller;
	import org.springframework.http.ResponseEntity;
	import org.springframework.validation.annotation.Validated;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.HttpStatus;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.server.ResponseStatusException;
	
	import com.document.authRequest.AuthRequest;
	import com.document.entity.Document;
	import com.document.exception.CustumException;
	import com.document.security.JwtSecure;
	import com.document.service.DocService;
	
	import jakarta.validation.Valid;
	import jakarta.validation.Valid;
	import jakarta.validation.constraints.NotBlank;
	import jakarta.validation.constraints.NotEmpty;
	//http://localhost:9090/api/document/welcome
	//http://localhost:9090/api/document/D101
	
	
	@RestController
	@RequestMapping("/api/document")
	public class DocController {
		
	@Autowired
	private DocService service;
	@Autowired
	    private JwtSecure jwtSecure;
	
//	    public DocController(DocService service, JwtSecure jwtSecure) {
//	        this.service = service;
//	        this.jwtSecure = jwtSecure;
//	        System.out.println("INSIDE CONTROLLER");
//	    }
//	
	    // ✔ PUBLIC API (no JWT required)
	 @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
	        if ("aish".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
	            String token = jwtSecure.generateToken(request.getUsername());
	            return ResponseEntity.ok(token);
	        }
	        return ResponseEntity.ok("Invalid username or password");
	    }
	    
	
	//        if (request.getUsername().equals("aish") && request.getPassword().equals("1234")) {
	//            return jwtSecure.generateToken(request.getUsername());
	//            
	//        }
	//        return "Invalid username or password";
	//   
	//    }
	
	    // ✔ SECURED
	    @PostMapping("/save")
	    public ResponseEntity<Document> createDocument(@Valid @RequestBody Document document) {
	
	//  	if(document.getDocTitle()==null || document.getDocTitle().isBlank()) {
	// 		throw new CustumException("doc title must not be null or empty"); 
	//   	}
	       Document saved = service.saveDocument(document);
	
	        return ResponseEntity
	                .status(HttpStatus.CREATED)   // ✅ 201 Created
	                .body(saved);
	       
	    }
	
	
	    @GetMapping("/alldoc")
	    public List<Document> getAllDoument() {
	        return service.getAllDocument();
	    }
	
	    @GetMapping("/byid/{id}")
	    public ResponseEntity<Document> getDocumentById(@PathVariable @NotEmpty(message="Id should not empty")String id) {
	    	 
	
	         return ResponseEntity.ok(service.getDocumentById(id));
	     }
	    
	
	    @PutMapping("/{id}")
	    public ResponseEntity<Document> updateDocument(
	            @PathVariable String id, @RequestBody Document update) {
	    	
	        Document old = service.getDocumentById(id);
	
	        if (old == null) {
	            throw new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "Document not found with id: " + id
	            );
	        }
	
	        old.setDocTitle(update.getDocTitle());
	        old.setFileName(update.getFileName());
	        old.setPath(update.getPath());
	        old.setVersion(update.getVersion());
	        old.setDocClassName(update.getDocClassName());
	
	        return ResponseEntity.ok(service.saveDocument(old));
	    }
	
	    @DeleteMapping("/{id}")
	    public String deleteDocument(@PathVariable String id) {
	        if (service.deleteDocument(id)) {
	            return "Document deleted successfully: " + id;
	        }
	        return "Document not found: " + id;
	    }
	
	    // ✔ Public welcome
//	    @GetMapping("/welcome")
//	    public String getMsg() {
//	        return "welcome postman";
//	    }
	}
	
