package com.pedinun.pedinunfood.dto.response;

import com.pedinun.pedinunfood.entity.usuario.Endereco;
import com.pedinun.pedinunfood.entity.usuario.TipoUsuario;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UsuarioResponse(
        long id,
        String nome,
        String email,
        String senha,
        String telefone,
        String cpf,
        TipoUsuario tipo,
        Long companyId,
        List<Endereco> enderecos,
        Boolean ativo,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
) {
}
