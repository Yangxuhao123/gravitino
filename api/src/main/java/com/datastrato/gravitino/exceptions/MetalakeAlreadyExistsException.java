/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.exceptions;

/** An exception thrown when the metalake already exists. */
public class MetalakeAlreadyExistsException extends AlreadyExistsException {

  public MetalakeAlreadyExistsException(String message) {
    super(message);
  }

  public MetalakeAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
