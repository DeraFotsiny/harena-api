package com.harena.api.model.exception;

public class Exception extends RuntimeException {
  private final ExceptionType type;

  public Exception(ExceptionType type, String message) {
    super(message);
    this.type = type;
  }

  public enum ExceptionType {
    BadRequestException,
    NotFoundException,
    NotAuthorizedException,
    ResourceNotFoundException,
    TooManyRequestsException,
    InternalServerException
  }
}
