package com.cursos_tamayoshi.exceptions;

public class NameFindException extends RuntimeException {
  public NameFindException() {
    super("Curso já existe");
  }
}
