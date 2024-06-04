package com.codecool.men.controller.exceptions;

public class NoteNotFoundException extends RuntimeException{
  public NoteNotFoundException(){
    super("Note not found!");
  }
}
