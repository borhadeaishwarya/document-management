package com.document.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


@ExtendWith(MockitoExtension.class)
class DocControllerTest {

    @InjectMocks    
    private DocController controller;

    @Mock
    private DocService service;

    @Mock
    private JwtSecure jwtSecure;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testUpdateDocument_Success() throws Exception {
    	Document old = new Document();
        old.setDocId("D101");
        old.setDocTitle("Old Title");
        old.setFileName("old.pdf");
        old.setPath("/old/path");
        old.setVersion("v1");
        old.setDocClassName("Old Class");

        // Updated document
        Document update = new Document();
        update.setDocId("D101");
        update.setDocTitle("Updated Title");
        update.setFileName("updated.pdf");
        update.setPath("/updated/path");
        update.setVersion("v2");
        update.setDocClassName("Updated Class");

        when(service.getDocumentById("D101")).thenReturn(old);
        when(service.saveDocument(any(Document.class))).thenReturn(update);

        mockMvc.perform(put("/api/document/D101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docTitle").value("Updated Title"))
                .andExpect(jsonPath("$.fileName").value("updated.pdf"))
                .andExpect(jsonPath("$.path").value("/updated/path"))
                .andExpect(jsonPath("$.version").value("v2"))
                .andExpect(jsonPath("$.docClassName").value("Updated Class"));

    }

    @Test
    void testUpdateDocument_NotFound() throws Exception {
        String id = "D999";

        when(service.getDocumentById(id)).thenReturn(null);

        mockMvc.perform(put("/api/document/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new Document("D999", "X", "y.pdf", "/path", "v1", "Class")
                )))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(service).getDocumentById(id);
        verify(service, never()).saveDocument(any());
    }
}
