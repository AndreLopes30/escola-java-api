package com.andre.escola_api.controller;

import com.andre.escola_api.model.Aluno;
import com.andre.escola_api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {
    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(aluno -> ResponseEntity.ok(aluno))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluno> salvar(@Valid @RequestBody Aluno aluno) {
        Aluno alunoSalvo = alunoService.salvar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id , @Valid @RequestBody Aluno aluno) {
        aluno.setId(id);
        return ResponseEntity.ok(alunoService.atualizar(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Aluno> deletar(@PathVariable Long id) {
        alunoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
