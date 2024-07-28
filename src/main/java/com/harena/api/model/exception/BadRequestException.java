package com.harena.api.model.exception;

public class BadRequestException extends Exception {
  public BadRequestException() {
    super(ExceptionType.BadRequestException, "Bad request");
  }
}
