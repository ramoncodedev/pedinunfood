package com.pedinun.pedinunfood.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemPedidoResponse(
        Long id,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal,
        String observacao
) {
}