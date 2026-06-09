package com.andre.escola_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoRequestDTO(
    @NotBlank
    String nome,

    @NotNull
    @Min(0)
    @Max(10)
    Double nota,

    String turma,

    @Min(0)
    Integer idade
) {

}
