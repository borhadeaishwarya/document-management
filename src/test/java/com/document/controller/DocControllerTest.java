package com.document.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.document.entity.Document;
import com.document.security.JwtSecure;
import com.document.service.DocService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DocController.class)
@AutoConfigureMockMvc(addFilters = false) 
class DocControllerTest {
//	
//	@InjectMocks
//    private DocController controller;

    @MockBean
    private DocService service;

    @MockBean
    private JwtSecure jwtSecure;
    
     @Autowired
    private MockMvc mockMvc;
     
    private ObjectMapper mapper = new ObjectMapper();

//    @BeforeEach
//    void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    }

    // ================= LOGIN =================

    @Test
    void testLoginSuccess() throws Exception {
        when(jwtSecure.generateToken("aish")).thenReturn("jwt-token");

        mockMvc.perform(post("/api/document/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"aish\",\"password\":\"1234\"}"))
                .andExpect(status().isOk())              // ✅ no semicolon here
                .andExpect(content().string("jwt-token"));
    }

    @Test
    void testLoginFailure() throws Exception {
        mockMvc.perform(post("/api/document/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"wrong\",\"password\":\"wrong\"}")) // ✅ use escaped quotes
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid username or password"));
    }

    // ================= CREATE =================

    @Test
    void testCreateDocument() throws Exception {
        Document doc = new Document("D101", "Title", "file.pdf", "/path", "v1", "Class");
        when(service.saveDocument(any(Document.class))).thenReturn(doc);

        mockMvc.perform(post("/api/document/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(doc)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.docId").value("D101"));
    }

    // ================= GET ALL =================

    @Test
    void testGetAllDocuments() throws Exception {
        when(service.getAllDocument()).thenReturn(List.of(
                new Document("D1","A","a.pdf","/a","v1","C1"),
                new Document("D2","B","b.pdf","/b","v2","C2")
        ));

        mockMvc.perform(get("/api/document/alldoc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    // ================= GET BY ID =================

    @Test
    void testGetDocumentById() throws Exception {

        Document doc = new Document(
                "D101","Title","file.pdf","/path","v1","Class");

        when(service.getDocumentById("D101")).thenReturn(doc);

        mockMvc.perform(get("/api/document/byid/D101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docTitle").value("Title"));
    }

    // ================= UPDATE =================

    @Test
    void testUpdateDocumentSuccess() throws Exception {

        Document old = new Document(
                "D101","Old","old.pdf","/old","v1","Old");

        Document updated = new Document(
                "D101","New","new.pdf","/new","v2","New");

        when(service.getDocumentById("D101")).thenReturn(old);
        when(service.saveDocument(any(Document.class))).thenReturn(updated);

        mockMvc.perform(put("/api/document/D101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docTitle").value("New"));
    }

    @Test
    void testUpdateDocumentNotFound() throws Exception {

        when(service.getDocumentById("D999")).thenReturn(null);

        Document update = new Document(
                "D999","X","x.pdf","/x","v1","C");

        mockMvc.perform(put("/api/document/D999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    // ================= DELETE =================

    @Test
    void testDeleteDocumentSuccess() throws Exception {

        when(service.deleteDocument("D101")).thenReturn(true);

        mockMvc.perform(delete("/api/document/D101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Document deleted successfully: D101"));
    }

    @Test
    void testDeleteDocumentNotFound() throws Exception {

        when(service.deleteDocument("D999")).thenReturn(false);

        mockMvc.perform(delete("/api/document/D999"))
                .andExpect(status().isOk())
                .andExpect(content().string("Document not found: D999"));
    }

    // ================= WELCOME =================

//    @Test
//    void testWelcome() throws Exception {
//
//    	mockMvc.perform(get("/api/document/welcome")
//    	        .header("Authorization", "Bearer mock-token"))
//    	        .andExpect(status().isOk());
//
//    }
}
