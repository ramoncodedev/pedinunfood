package com.pedinun.pedinunfood.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        String descricao,
        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser positivo")
        BigDecimal preco,
        String imagemUrl,
        Boolean ativo,
        Boolean destaque,
        Long categoriaId
) {
}