package com.codecool.men.controller.exceptions;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException(){
    super("No user found!");
  }
}
