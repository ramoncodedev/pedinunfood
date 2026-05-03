package com.pedinun.pedinunfood.dto.request;

import com.pedinun.pedinunfood.entity.pedido.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusPedidoRequest(
        @NotNull(message = "O status é obrigatório")
        StatusPedido status
) {
}