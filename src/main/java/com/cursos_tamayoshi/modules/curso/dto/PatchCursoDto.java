package com.cursos_tamayoshi.modules.curso.dto;

import com.cursos_tamayoshi.modules.curso.enums.Active;

import jakarta.validation.constraints.NotNull;

public record PatchCursoDto(
    @NotNull
    Active active
) {}
