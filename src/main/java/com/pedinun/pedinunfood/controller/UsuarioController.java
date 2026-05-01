package com.pedinun.pedinunfood.controller;


import com.pedinun.pedinunfood.dto.request.UsuarioRequest;
import com.pedinun.pedinunfood.dto.response.UsuarioResponse;
import com.pedinun.pedinunfood.entity.usuario.Usuario;
import com.pedinun.pedinunfood.mapper.UsuarioMapper;
import com.pedinun.pedinunfood.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuarioEntity = UsuarioMapper.toEntity(usuarioRequest);
        Usuario usuarioSalvo = usuarioService.saveUsuario(usuarioEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toResponse(usuarioSalvo));
    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioResponse> findByTelefone( String telefone) {
        Usuario usuario = usuarioService.findByTelefone(telefone);
        return ResponseEntity.ok(UsuarioMapper.toResponse(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioResponse> usuarioResponses = usuarios.stream()
                .map(UsuarioMapper::toResponse)
                .toList();

        return ResponseEntity.ok(usuarioResponses);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByEmail(@RequestParam String email) {
        usuarioService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }


}
