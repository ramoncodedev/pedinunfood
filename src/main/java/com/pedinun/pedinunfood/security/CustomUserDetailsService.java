package com.pedinun.pedinunfood.security;

import com.pedinun.pedinunfood.entity.usuario.Usuario;
import com.pedinun.pedinunfood.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }

        Long companyId = null;
        if (usuario.getCompany() != null) {
            companyId = usuario.getCompany().getId();
        }

        log.info("Loading user: {} - Password in DB starts with: {}", email, usuario.getSenha().substring(0, Math.min(10, usuario.getSenha().length())));

        return new UsuarioPrincipal(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipo().name(),
                companyId,
                usuario.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getTipo().name()))
        );
    }
}