package com.andre.escola_api.controller;

import com.andre.escola_api.dto.DadosCadastroUsuario;
import com.andre.escola_api.model.Usuario;
import com.andre.escola_api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario = new Usuario();

        usuario.setLogin(dados.login());

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        usuario.setSenha(senhaCriptografada);

        repository.save(usuario);

        return ResponseEntity.ok().build();
    }
}