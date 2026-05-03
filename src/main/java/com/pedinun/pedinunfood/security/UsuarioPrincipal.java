package com.pedinun.pedinunfood.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class UsuarioPrincipal implements UserDetails {

    private final Long usuarioId;
    private final String email;
    private final String tipo;
    private final Long companyId;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return usuarioId;
    }

    public boolean isCliente() {
        return "CLIENTE".equals(tipo);
    }

    public boolean isLogista() {
        return "LOGISTA".equals(tipo);
    }

    public boolean isAdmin() {
        return "ADMIN".equals(tipo);
    }
}