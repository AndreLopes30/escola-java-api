package com.andre.escola_api.service;

import com.andre.escola_api.model.Aluno;
import com.andre.escola_api.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public Aluno salvar(Aluno aluno) {
        if (aluno.getNota() < 0 || aluno.getNota() > 10) {
            throw new IllegalArgumentException("Nota inválida");
        }
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletarPorId(Long id) {
        alunoRepository.deleteById(id);
    }

    public Aluno atualizar (Aluno aluno) {
        if (aluno.getNota() < 0 || aluno.getNota() > 10) {
            throw new IllegalArgumentException("Nota inválida");
        }
        return alunoRepository.save(aluno);
    }

}
