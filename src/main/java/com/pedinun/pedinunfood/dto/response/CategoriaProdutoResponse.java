package com.pedinun.pedinunfood.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoriaProdutoResponse(
        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}