package com.andre.escola_api.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(@NotBlank String login, @NotBlank String senha) {
    
}

