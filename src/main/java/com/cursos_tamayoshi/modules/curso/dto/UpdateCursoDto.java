package com.cursos_tamayoshi.modules.curso.dto;

import com.cursos_tamayoshi.modules.curso.enums.Active;

import jakarta.validation.constraints.Size;


public record UpdateCursoDto(
    @Size(max = 120) String name,
    @Size(max = 255) String category,
    Active active // "ATIVO" | "INATIVO" (se usar enum, troque para Status e remova conversão)
) {}
