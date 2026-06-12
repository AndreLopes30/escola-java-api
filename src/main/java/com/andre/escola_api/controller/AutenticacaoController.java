package com.andre.escola_api.controller;

import com.andre.escola_api.dto.DadosAutenticacao;
import com.andre.escola_api.dto.DadosCadastroUsuario;
import com.andre.escola_api.model.Usuario;
import com.andre.escola_api.repository.UsuarioRepository;
import com.andre.escola_api.service.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AutenticacaoController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    public record DadosTokenJWT(String token) {
    }

    @RestController
    @RequestMapping("/usuarios")
    @RequiredArgsConstructor
    public class UsuarioController {

        private final UsuarioRepository repository;
        private final PasswordEncoder passwordEncoder;

        @PostMapping
        @Transactional
        public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
            var usuario = new Usuario(dados);

            String senhaCriptografada = passwordEncoder.encode(dados.senha());
            usuario.setSenha(senhaCriptografada);

            repository.save(usuario);
            return ResponseEntity.ok().build();
        }
    }
}
