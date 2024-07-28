package com.harena.api.model.exception;

public class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException() {
    super(ExceptionType.ResourceNotFoundException, "Ressource not found");
  }
}
