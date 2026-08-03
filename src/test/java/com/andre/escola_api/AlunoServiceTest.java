package com.andre.escola_api;

import com.andre.escola_api.model.Aluno;
import com.andre.escola_api.repository.AlunoRepository;
import com.andre.escola_api.service.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    void cadastrarComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setNome("André Teste");
        aluno.setNota(10.0);

        Mockito.when(alunoRepository.save(aluno))
                .thenReturn(aluno);

        Aluno resultado = alunoService.salvar(aluno);

        Assertions.assertEquals("André Teste", resultado.getNome());
        Assertions.assertEquals(10.0, resultado.getNota());

        Mockito.verify(alunoRepository).save(aluno);
    }

    @Test
    void notaInvalida() {
        Aluno aluno = new Aluno();
        aluno.setNome("André Teste");
        aluno.setNota(11.0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> alunoService.salvar(aluno));

        Mockito.verifyNoInteractions(alunoRepository);
    }

    @Test
    void buscarAlunoExistente() {
        Aluno aluno = new Aluno();
        aluno.setId(10L);
        aluno.setNome("André Teste");
        aluno.setNota(10.0);

        Mockito.when(alunoRepository.findById(10L))
                .thenReturn(Optional.of(aluno));

        Optional<Aluno> resultado = alunoService.buscarPorId(aluno.getId());

        Assertions.assertEquals(10L, resultado.get().getId());

        Mockito.verify(alunoRepository).findById(resultado.get().getId());
    }

    @Test
    void bucarAlunoInexistente() {

        Mockito.when(alunoRepository.findById(10L))
                .thenReturn(Optional.empty());

        Optional<Aluno> resultado = alunoService.buscarPorId(10L);

        Assertions.assertTrue(resultado.isEmpty());

        Mockito.verify(alunoRepository).findById(10L);
    }

    @Test
    void excluirAluno() {

        alunoService.deletarPorId(10L);

        Mockito.verify(alunoRepository).deleteById(10L);
    }

    @Test
    void listarALunos() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("André");
        aluno1.setNota(8.0);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("João");
        aluno2.setNota(3.5);

        List<Aluno> alunosList = List.of(aluno1, aluno2);

        Mockito.when(alunoRepository.findAll())
                .thenReturn(alunosList);

        List<Aluno> resultado = alunoService.listarTodos();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("André", resultado.get(0).getNome());

        Mockito.verify(alunoRepository).findAll();
    }

}
