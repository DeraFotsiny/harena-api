package com.harena.api.model.exception;

public class InternalServerException extends Exception {
  public InternalServerException() {
    super(ExceptionType.InternalServerException, "Unexpected error");
  }
}
