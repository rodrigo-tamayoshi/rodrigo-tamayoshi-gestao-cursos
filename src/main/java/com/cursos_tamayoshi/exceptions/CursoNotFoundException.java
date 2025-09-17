package com.cursos_tamayoshi.exceptions;

public class CursoNotFoundException extends RuntimeException{

    public CursoNotFoundException() { super("Curso não encontrado"); }
    
}
