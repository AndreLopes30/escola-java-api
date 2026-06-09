package com.andre.escola_api.service;
import com.andre.escola_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (usuarioRepository.findByLogin(username) == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return usuarioRepository.findByLogin(username);

    }
}
