package com.harena.api.model.exception;

public class NotAuthorizedException extends Exception {
  public NotAuthorizedException() {
    super(ExceptionType.NotAuthorizedException, "Not authorized");
  }
}
