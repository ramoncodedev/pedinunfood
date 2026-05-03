package com.pedinun.pedinunfood.controller;

import com.pedinun.pedinunfood.config.JwtUtil;
import com.pedinun.pedinunfood.dto.request.AuthLoginRequest;
import com.pedinun.pedinunfood.dto.request.AuthRegisterRequest;
import com.pedinun.pedinunfood.dto.response.AuthResponse;
import com.pedinun.pedinunfood.dto.response.UsuarioResponse;
import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;
import com.pedinun.pedinunfood.entity.usuario.Usuario;
import com.pedinun.pedinunfood.mapper.UsuarioMapper;
import com.pedinun.pedinunfood.repository.usuario.UsuarioRepository;
import com.pedinun.pedinunfood.security.UsuarioPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();

        String token = jwtUtil.generateToken(
                principal.getUsuarioId(),
                principal.getEmail(),
                principal.getTipo(),
                principal.getCompanyId()
        );

        AuthResponse response = new AuthResponse(
                token,
                principal.getUsuarioId(),
                principal.getUsername(),
                principal.getEmail(),
                TipoUsuario.valueOf(principal.getTipo()),
                principal.getCompanyId()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRegisterRequest request) {
        try {
            if (usuarioRepository.findByEmail(request.email()) != null) {
                return ResponseEntity.badRequest().body("{\"message\": \"Email já cadastrado\"}");
            }

            if (usuarioRepository.findByTelefone(request.telefone()) != null) {
                return ResponseEntity.badRequest().body("{\"message\": \"Telefone já cadastrado\"}");
            }

            Usuario usuario = Usuario.builder()
                    .nome(request.nome())
                    .email(request.email())
                    .senha(passwordEncoder.encode(request.senha()))
                    .telefone(request.telefone())
                    .cpf(request.cpf())
                    .tipo(request.tipo())
                    .ativo(true)
                    .build();

            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            Long companyId = null;
            if (request.tipo() == TipoUsuario.LOGISTA && usuario.getCompany() != null) {
                companyId = usuario.getCompany().getId();
            }

            String token = jwtUtil.generateToken(
                    usuarioSalvo.getId(),
                    usuarioSalvo.getEmail(),
                    usuarioSalvo.getTipo().name(),
                    companyId
            );

            AuthResponse response = new AuthResponse(
                    token,
                    usuarioSalvo.getId(),
                    usuarioSalvo.getNome(),
                    usuarioSalvo.getEmail(),
                    usuarioSalvo.getTipo(),
                    companyId
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Erro ao cadastrar usuário: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> getMe(Authentication authentication) {
        UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findById(principal.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(UsuarioMapper.toResponse(usuario));
    }
}