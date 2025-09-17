package com.cursos_tamayoshi.modules.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

import com.cursos_tamayoshi.exceptions.CursoNotFoundException;
import com.cursos_tamayoshi.modules.curso.CursoEntity;
import com.cursos_tamayoshi.modules.curso.CursoRepository;
import com.cursos_tamayoshi.modules.curso.dto.PatchCursoDto;
import com.cursos_tamayoshi.modules.curso.dto.UpdateCursoDto;
import com.cursos_tamayoshi.modules.curso.useCases.CreateCursoUseCase;
import com.cursos_tamayoshi.modules.curso.useCases.DeleteCursoUseCase;
import com.cursos_tamayoshi.modules.curso.useCases.UpdateCursoUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Curso", description = "Informações do Curso")

public class CursoController {

    @Autowired
    private CreateCursoUseCase createCursoUseCase;

    @Autowired
    private UpdateCursoUseCase updateCursoUseCase;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DeleteCursoUseCase deleteCursoUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de curso", description = "Essa função é responsável por cadastrar um cruso.")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CursoEntity.class)))
    public ResponseEntity<Object> create(@Valid @RequestBody CursoEntity cursoEntity) {
        try {
            var result = createCursoUseCase.execute(cursoEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    @Operation(summary = "Listagem de cursos cadastradaos", description = "Essa função é responsável por listar todos os cursos cadastrados")
    @ApiResponses(@ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = CursoEntity.class)))
    }))
    public List<CursoEntity> listarTodos() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CursoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso", description = "Atualiza dados do curso. Garante nome único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CursoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
            @ApiResponse(responseCode = "409", description = "Nome já existe")
    })
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @Valid @RequestBody UpdateCursoDto body) {
        var atualizado = updateCursoUseCase.execute(id, body);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar curso", description = "Deleta curso pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CursoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<Void> atualizar(@PathVariable UUID id) {
        deleteCursoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Atualizar status do curso", description = "Ativa ou inativa um curso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = CursoEntity.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
            @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    public ResponseEntity<?> atualizarStatus(@PathVariable UUID id,
            @Valid @RequestBody PatchCursoDto body) {
        var curso = cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException());

        curso.setActive(body.active());

        var atualizado = cursoRepository.save(curso);
        return ResponseEntity.ok(atualizado);
    }

}
