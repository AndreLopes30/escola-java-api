package com.andre.escola_api;

import com.andre.escola_api.model.Aluno;
import com.andre.escola_api.repository.AlunoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

@DataJpaTest
@Testcontainers
class AlunoRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void salvarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        alunoRepository.saveAndFlush(aluno);

        Assertions.assertNotNull(aluno.getId());

        entityManager.clear();

        Optional<Aluno> alunoSalvo = alunoRepository.findById(aluno.getId());

        Assertions.assertTrue(alunoSalvo.isPresent());
        Assertions.assertEquals("André", alunoSalvo.get().getNome());
        Assertions.assertEquals(8.0, alunoSalvo.get().getNota());
    }

    @Test
    void salvarEExcluirAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        alunoRepository.saveAndFlush(aluno);

        Optional<Aluno> alunoSalvo = alunoRepository.findById(aluno.getId());

        alunoRepository.deleteById(alunoSalvo.get().getId());
        alunoRepository.flush();
        entityManager.clear();

        Optional<Aluno> alunoExcluido =
                alunoRepository.findById(aluno.getId());

        Assertions.assertTrue(alunoExcluido.isEmpty());

    }
}