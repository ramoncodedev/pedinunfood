package com.pedinun.pedinunfood.dto.response;

import com.pedinun.pedinunfood.entity.pedido.StatusPedido;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PedidoResponse(
        Long id,
        Long clienteId,
        String clienteNome,
        Long restauranteId,
        String restauranteNome,
        StatusPedido status,
        Long enderecoEntregaId,
        String enderecoEntregaTexto,
        List<ItemPedidoResponse> itens,
        BigDecimal subtotal,
        BigDecimal taxaEntrega,
        BigDecimal valorTotal,
        String observacao,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}