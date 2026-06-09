package com.andre.escola_api.dto;

public record AlunoResponseDTO(
        Long id,
        String nome,
        Double nota,
        String turma,
        Integer idade
) {
}
