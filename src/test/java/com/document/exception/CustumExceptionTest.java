package com.document.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class CustumExceptionTest {

	 @Test
	    void testCustomExceptionMessage() {
	        CustumException ex = new CustumException("Something went wrong");

	        assertEquals("Something went wrong", ex.getMessage());
	    }
}
