package com.cursos_tamayoshi.modules.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
