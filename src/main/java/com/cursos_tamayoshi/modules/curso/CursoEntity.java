package com.cursos_tamayoshi.modules.curso;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cursos_tamayoshi.modules.curso.enums.Active;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "curso")
public class CursoEntity {


    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String category;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Active active;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private Instant updatedAt;


}