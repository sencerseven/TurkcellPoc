package com.turkcell.poc.advice;

import com.turkcell.poc.model.ApiResponse;
import javax.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      EntityNotFoundException ex) {
    ApiResponse apiResponse = new ApiResponse(HttpStatus.NO_CONTENT);
    apiResponse.setMessage(ex.getMessage());
    return buildResponseEntity(apiResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      IllegalArgumentException ex) {
    ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST);
    apiResponse.setMessage(ex.getMessage());
    return buildResponseEntity(apiResponse);
  }

  @ExceptionHandler(IllegalStateException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      IllegalStateException ex) {
    ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_ACCEPTABLE);
    apiResponse.setMessage(ex.getMessage());
    return buildResponseEntity(apiResponse);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      Exception ex) {
    ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND);
    apiResponse.setMessage(ex.getMessage());
    return buildResponseEntity(apiResponse);
  }




  private ResponseEntity<Object> buildResponseEntity(ApiResponse apiResponse) {
    return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }
}
