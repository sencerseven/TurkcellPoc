package com.turkcell.poc.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ApiResponse<T> implements Serializable {

  private static final long serialVersionUID = 4433349036686606954L;

  private HttpStatus status;
  private LocalDateTime timestamp;
  private T body;
  private String message;
  private List<ApiSubError> error;

  public ApiResponse() {
    timestamp = LocalDateTime.now();
  }

  public ApiResponse(HttpStatus status) {
    this();
    this.status = status;
  }

  public ApiResponse(HttpStatus status, T body) {
    this();
    this.status = status;
    this.body = body;
  }

  public ApiResponse(HttpStatus status, T body, String message) {
    this();
    this.status = status;
    this.body = body;
    this.message = message;
  }

  public ApiResponse(HttpStatus status, LocalDateTime timestamp, T body, String message,
      List<ApiSubError> error) {
    this.status = status;
    this.timestamp = timestamp;
    this.body = body;
    this.message = message;
    this.error = error;
  }

  abstract class ApiSubError {

  }

  @Data
  @EqualsAndHashCode(callSuper = false)
  @AllArgsConstructor
  class ApiValidationError extends ApiSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String object, String message) {
      this.object = object;
      this.message = message;
    }
  }
}