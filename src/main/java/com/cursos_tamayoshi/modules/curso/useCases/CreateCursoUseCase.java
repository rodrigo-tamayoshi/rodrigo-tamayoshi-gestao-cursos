package com.cursos_tamayoshi.modules.curso.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursos_tamayoshi.exceptions.NameFindException;
import com.cursos_tamayoshi.modules.curso.CursoEntity;
import com.cursos_tamayoshi.modules.curso.CursoRepository;

@Service
public class CreateCursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoEntity execute(CursoEntity curso) {
        if (cursoRepository.existsByNameIgnoreCase(curso.getName())) {
            throw new NameFindException();
        }
        return this.cursoRepository.save(curso);
    }
}
