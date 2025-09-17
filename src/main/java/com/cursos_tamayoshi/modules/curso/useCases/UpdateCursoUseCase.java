package com.cursos_tamayoshi.modules.curso.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursos_tamayoshi.exceptions.CursoNotFoundException;
import com.cursos_tamayoshi.exceptions.NameFindException;
import com.cursos_tamayoshi.modules.curso.CursoEntity;
import com.cursos_tamayoshi.modules.curso.CursoRepository;
import com.cursos_tamayoshi.modules.curso.dto.UpdateCursoDto;

import jakarta.transaction.Transactional;

@Service
public class UpdateCursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

        public UpdateCursoUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public CursoEntity execute(UUID id, UpdateCursoDto updateCursoDto) {
        var curso = cursoRepository.findById(id).orElseThrow(CursoNotFoundException::new);

        // Nome: se veio e mudou, checa duplicidade
        if (updateCursoDto.name() != null && !updateCursoDto.name().equalsIgnoreCase(curso.getName())) {
            if (cursoRepository.existsByNameIgnoreCaseAndIdNot(updateCursoDto.name(), id)) {
                throw new NameFindException();
            }
            curso.setName(updateCursoDto.name());
        }

        if (updateCursoDto.category() != null) {
            curso.setCategory(updateCursoDto.category());
        }

        if (updateCursoDto.active() != null) {
            // Se usar enum: curso.setStatus(Status.valueOf(req.status().toUpperCase()));
            curso.setActive(updateCursoDto.active()); // ajuste ao seu modelo real
        }

        return cursoRepository.save(curso);
    }
    
}
