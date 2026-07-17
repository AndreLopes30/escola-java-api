
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
    }

    @Test
    void salvarComNotaDez_deveSalvar() {
    }

    @Test
    void listarALunos() {
    }

}
