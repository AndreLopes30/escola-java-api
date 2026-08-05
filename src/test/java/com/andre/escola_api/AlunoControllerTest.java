package com.andre.escola_api;

import com.andre.escola_api.config.SecurityFilter;
import com.andre.escola_api.controller.AlunoController;
import com.andre.escola_api.dto.AlunoRequestDTO;
import com.andre.escola_api.model.Aluno;
import com.andre.escola_api.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AlunoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private AlunoService alunoService;

    @MockitoBean
    private SecurityFilter securityFilter;


    @Test
    void listarTodos_deveRetornarListaDeAlunos() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        when(alunoService.listarTodos())
                .thenReturn(List.of(aluno));

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("André"))
                .andExpect(jsonPath("$[0].nota").value(8.0))
                .andExpect(jsonPath("$[0].turma").value("A"))
                .andExpect(jsonPath("$[0].idade").value(20));

        Mockito.verify(alunoService).listarTodos();

    }

    @Test
    void buscarPorId_quandoAlunoExistir_deveRetornarStatus200() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        when(alunoService.buscarPorId(aluno.getId()))
                .thenReturn(Optional.of(aluno));

        mockMvc.perform(get("/alunos/{id}", aluno.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("André"))
                .andExpect(jsonPath("$.nota").value(8.0))
                .andExpect(jsonPath("$.turma").value("A"))
                .andExpect(jsonPath("$.idade").value(20));

        Mockito.verify(alunoService).buscarPorId(aluno.getId());
    }

    @Test
    void buscarPorId_quandoAlunoNaoExistir_deveRetornarStatus404() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setId(99L);
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        when(alunoService.buscarPorId(aluno.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/alunos/{id}", aluno.getId()))
                .andExpect(status().isNotFound());

        Mockito.verify(alunoService).buscarPorId(aluno.getId());
    }

    @Test
    void salvarComDadosValidos_deveRetornarStatus201() throws Exception {

        AlunoRequestDTO dto = new AlunoRequestDTO(
                "André",8.0, "A", 20
        );

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        when(alunoService.salvar(any(Aluno.class)))
                .thenReturn(aluno);

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("André"))
                .andExpect(jsonPath("$.nota").value(8.0))
                .andExpect(jsonPath("$.turma").value("A"))
                .andExpect(jsonPath("$.idade").value(20));

        Mockito.verify(alunoService).salvar(any(Aluno.class));
    }

    @Test
    void salvarComDadosInvalidos_deveRetornarStatus400ENaoChamarService() throws Exception {

        AlunoRequestDTO dto = new AlunoRequestDTO(
                "",8.0, "A", 20
        );

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(alunoService);

    }

    @Test
    void deletar_deveRetornarStatus204() throws Exception {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("André");
        aluno.setNota(8.0);
        aluno.setTurma("A");
        aluno.setIdade(20);

        mockMvc.perform(delete("/alunos/{id}", aluno.getId()))
                .andExpect(status().isNoContent());

        Mockito.verify(alunoService).deletarPorId(aluno.getId());
    }

}
