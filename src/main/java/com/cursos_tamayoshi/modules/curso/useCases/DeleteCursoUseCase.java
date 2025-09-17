package com.cursos_tamayoshi.modules.curso.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cursos_tamayoshi.exceptions.CursoNotFoundException;
import com.cursos_tamayoshi.modules.curso.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class DeleteCursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

    public DeleteCursoUseCase (CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public void execute(UUID id){
        try{
            cursoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new CursoNotFoundException();
        }
    }
    
}
