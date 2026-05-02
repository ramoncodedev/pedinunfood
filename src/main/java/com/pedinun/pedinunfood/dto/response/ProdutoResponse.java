package com.pedinun.pedinunfood.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        String imagemUrl,
        Boolean ativo,
        Boolean destaque,
        Long categoriaId,
        String categoriaNome,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}