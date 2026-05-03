package com.pedinun.pedinunfood.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record PedidoRequest(
        @NotNull(message = "O ID do restaurante é obrigatório")
        Long restauranteId,
        @NotNull(message = "O ID do endereço de entrega é obrigatório")
        Long enderecoEntregaId,
        @NotEmpty(message = "O pedido deve ter pelo menos um item")
        List<ItemPedidoRequest> itens,
        String observacao,
        BigDecimal taxaEntrega
) {
}