package com.pedinun.pedinunfood.security;

import com.pedinun.pedinunfood.config.JwtUtil;
import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;
import com.pedinun.pedinunfood.repository.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        if (StringUtils.hasText(token) && jwtUtil.isTokenValid(token)) {
            Long usuarioId = jwtUtil.extractUsuarioId(token);
            String email = jwtUtil.extractEmail(token);
            String tipo = jwtUtil.extractTipo(token);
            Long companyId = jwtUtil.extractCompanyId(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsuarioPrincipal principal = new UsuarioPrincipal(
                    usuarioId,
                    email,
                    tipo,
                    companyId,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + tipo))
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}