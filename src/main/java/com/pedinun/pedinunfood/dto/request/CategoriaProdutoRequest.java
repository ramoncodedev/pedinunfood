package com.pedinun.pedinunfood.dto.request;

public record CategoriaProdutoRequest(
        String nome,
        String descricao,
        Boolean ativo
) {
}