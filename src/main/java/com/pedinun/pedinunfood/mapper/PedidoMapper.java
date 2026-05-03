package com.pedinun.pedinunfood.mapper;

import com.pedinun.pedinunfood.dto.response.ItemPedidoResponse;
import com.pedinun.pedinunfood.dto.response.PedidoResponse;
import com.pedinun.pedinunfood.entity.pedido.ItemPedido;
import com.pedinun.pedinunfood.entity.pedido.Pedido;
import com.pedinun.pedinunfood.entity.usuario.Endereco;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PedidoMapper {

    public static String formatarEndereco(Endereco endereco) {
        if (endereco == null) return "";
        return String.format("%s, %s, %s, %s - %s/%s",
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento() != null ? endereco.getComplemento() : "",
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf());
    }

    public static ItemPedidoResponse toItemResponse(ItemPedido item) {
        return ItemPedidoResponse.builder()
                .id(item.getId())
                .produtoId(item.getProduto().getId())
                .produtoNome(item.getProduto().getNome())
                .quantidade(item.getQuantidade())
                .precoUnitario(item.getPrecoUnitario())
                .subtotal(item.getSubtotal())
                .observacao(item.getObservacao())
                .build();
    }

    public static PedidoResponse toResponse(Pedido pedido) {
        List<ItemPedidoResponse> itensResponse = pedido.getItens().stream()
                .map(PedidoMapper::toItemResponse)
                .toList();

        String enderecoTexto = "";
        if (pedido.getEnderecoEntrega() != null) {
            enderecoTexto = formatarEndereco(pedido.getEnderecoEntrega());
        }

        return PedidoResponse.builder()
                .id(pedido.getId())
                .clienteId(pedido.getCliente().getId())
                .clienteNome(pedido.getCliente().getNome())
                .restauranteId(pedido.getRestaurante().getId())
                .restauranteNome(pedido.getRestaurante().getNomeFantasia())
                .status(pedido.getStatus())
                .enderecoEntregaId(pedido.getEnderecoEntrega().getId())
                .enderecoEntregaTexto(enderecoTexto)
                .itens(itensResponse)
                .subtotal(pedido.getSubtotal())
                .taxaEntrega(pedido.getTaxaEntrega())
                .valorTotal(pedido.getValorTotal())
                .observacao(pedido.getObservacao())
                .dataCriacao(pedido.getDataCriacao())
                .dataAtualizacao(pedido.getDataAtualizacao())
                .build();
    }
}