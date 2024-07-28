package com.harena.api.model.exception;

public class TooManyRequestsException extends Exception {
  public TooManyRequestsException() {
    super(ExceptionType.TooManyRequestsException, "Too many requests");
  }
}
