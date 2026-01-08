package com.document.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.document.dao.DocDao;
import com.document.entity.Document;

@ExtendWith(MockitoExtension.class)
public class DocServiceTest {
	 @Mock
	    private DocDao dao;

	    @InjectMocks
	    private DocService docService;

	    private Document document;

	    @BeforeEach
	    void setUp() {
	    	
	    	        document = new Document();
	    	        document.setDocId("DOC1");
	    	        document.setDocTitle("Test Document");
	    	    }

	    	    // ================= save =================
	    	    @Test
	    	    void testSaveDocument() {
	    	        when(dao.save(any(Document.class))).thenReturn(document);

	    	        Document result = docService.saveDocument(document);

	    	        assertNotNull(result);
	    	        assertEquals("DOC1", result.getDocId());
	    	        verify(dao, times(1)).save(document);
	    	    }

	    	    // ================= get by id (found) =================
	    	    @Test
	    	    void testGetDocumentById_Found() {
	    	        when(dao.findById("DOC1")).thenReturn(Optional.of(document));

	    	        Document result = docService.getDocumentById("DOC1");

	    	        assertNotNull(result);
	    	        assertEquals("Test Document", result.getDocTitle());
	    	        verify(dao).findById("DOC1");
	    	    }

	    	    // ================= get by id (not found) =================
	    	    @Test
	    	    void testGetDocumentById_NotFound() {
	    	        // NO stubbing needed – default is Optional.empty()

	    	        Document result = docService.getDocumentById("DOC2");

	    	        assertNull(result);
	    	        verify(dao).findById("DOC2");
	    	    }

	    	    // ================= get all =================
	    	    @Test
	    	    void testGetAllDocument() {
	    	        when(dao.findAll()).thenReturn(List.of(document));

	    	        List<Document> result = docService.getAllDocument();

	    	        assertEquals(1, result.size());
	    	        verify(dao).findAll();
	    	    }

	    	    // ================= delete =================
	    	    @Test
	    	    void testDeleteDocument() {
	    	        doNothing().when(dao).deleteById("DOC1");

	    	        boolean result = docService.deleteDocument("DOC1");

	    	        assertTrue(result); // ✅ matches service implementation
	    	        verify(dao).deleteById("DOC1");
	    	    }
	}

