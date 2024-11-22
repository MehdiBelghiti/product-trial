package com.project.ecommerce.controller;

import com.project.ecommerce.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RestExceptionHandler
 * This is just an example to highlight the use of @ControllerAdvice for handling exceptions in this test project.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(
      Exception ex, WebRequest request) {

    if (ex instanceof ResourceNotFoundException) {
      ResourceNotFoundException hex = (ResourceNotFoundException) ex;
      return new ResponseEntity<Object>(hex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Object>(
        "Resource not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
  }
}
