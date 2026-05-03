package com.pedinun.pedinunfood.dto.response;

import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;

public record AuthResponse(
        String token,
        Long usuarioId,
        String nome,
        String email,
        TipoUsuario tipo,
        Long companyId
) {
}