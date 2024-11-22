package com.project.ecommerce.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

  @Test
  void testResourceNotFoundException() {
    String message = "Resource not found";
    ResourceNotFoundException exception = new ResourceNotFoundException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }
}