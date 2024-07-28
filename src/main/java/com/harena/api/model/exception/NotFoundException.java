package com.harena.api.model.exception;

public class NotFoundException extends Exception {
  public NotFoundException() {
    super(ExceptionType.NotFoundException, "Not found");
  }
}
