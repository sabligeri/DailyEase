package com.codecool.men.controller.exceptions;

public class OperationFailedException extends RuntimeException{
  public OperationFailedException(String m) {
    super("Operation failed!:" +
            m);
  }
}
