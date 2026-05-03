package com.pedinun.pedinunfood.service;

import com.pedinun.pedinunfood.dto.request.AtualizarStatusPedidoRequest;
import com.pedinun.pedinunfood.dto.request.ItemPedidoRequest;
import com.pedinun.pedinunfood.dto.request.PedidoRequest;
import com.pedinun.pedinunfood.dto.response.PedidoResponse;
import com.pedinun.pedinunfood.entity.company.Company;
import com.pedinun.pedinunfood.entity.pedido.ItemPedido;
import com.pedinun.pedinunfood.entity.pedido.Pedido;
import com.pedinun.pedinunfood.entity.pedido.StatusPedido;
import com.pedinun.pedinunfood.entity.usuario.Endereco;
import com.pedinun.pedinunfood.entity.usuario.Usuario;
import com.pedinun.pedinunfood.exception.ConflictionException;
import com.pedinun.pedinunfood.mapper.PedidoMapper;
import com.pedinun.pedinunfood.repository.company.CompanyRepository;
import com.pedinun.pedinunfood.repository.pedido.PedidoRepository;
import com.pedinun.pedinunfood.repository.produto.ProdutoRepository;
import com.pedinun.pedinunfood.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompanyRepository companyRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request, Long clienteId) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new ConflictionException("Cliente não encontrado."));

        Company restaurante = companyRepository.findById(request.restauranteId())
                .orElseThrow(() -> new ConflictionException("Restaurante não encontrado."));

        Endereco enderecoEntrega = cliente.getEnderecos().stream()
                .filter(e -> e.getId().equals(request.enderecoEntregaId()))
                .findFirst()
                .orElseThrow(() -> new ConflictionException("Endereço de entrega não encontrado."));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .restaurante(restaurante)
                .enderecoEntrega(enderecoEntrega)
                .status(StatusPedido.AGUARDANDO_CONFIRMACAO)
                .observacao(request.observacao())
                .build();

        BigDecimal taxaEntrega = request.taxaEntrega() != null ? request.taxaEntrega() : restaurante.getTaxaEntregaPadrao();
        pedido.setTaxaEntrega(taxaEntrega);

        for (ItemPedidoRequest itemRequest : request.itens()) {
            var produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new ConflictionException("Produto não encontrado: " + itemRequest.produtoId()));

            if (!produto.getAtivo()) {
                throw new ConflictionException("Produto indisponível: " + produto.getNome());
            }

            ItemPedido itemPedido = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(itemRequest.quantidade())
                    .precoUnitario(produto.getPreco())
                    .observacao(itemRequest.observacao())
                    .build();
            itemPedido.calcularSubtotal();

            pedido.adicionarItem(itemPedido);
        }

        pedido.calcularTotal();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return PedidoMapper.toResponse(pedidoSalvo);
    }

    @Transactional
    public PedidoResponse cancelarPedido(Long pedidoId, Long clienteId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ConflictionException("Pedido não encontrado."));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new ConflictionException("Você não tem permissão para cancelar este pedido.");
        }

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_CONFIRMACAO) {
            throw new ConflictionException("Não é possível cancelar o pedido. Status atual: " + pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        Pedido pedidoCancelado = pedidoRepository.save(pedido);

        return PedidoMapper.toResponse(pedidoCancelado);
    }

    @Transactional
    public PedidoResponse atualizarStatus(Long pedidoId, AtualizarStatusPedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ConflictionException("Pedido não encontrado."));

        StatusPedido novoStatus = request.status();
        StatusPedido statusAtual = pedido.getStatus();

        validarTransicaoStatus(statusAtual, novoStatus);

        pedido.setStatus(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return PedidoMapper.toResponse(pedidoAtualizado);
    }

    private void validarTransicaoStatus(StatusPedido atual, StatusPedido novo) {
        boolean transicaoValida = switch (atual) {
            case AGUARDANDO_CONFIRMACAO -> novo == StatusPedido.CONFIRMADO || novo == StatusPedido.CANCELADO;
            case CONFIRMADO -> novo == StatusPedido.EM_PREPARACAO || novo == StatusPedido.CANCELADO;
            case EM_PREPARACAO -> novo == StatusPedido.SAIU_PARA_ENTREGA;
            case SAIU_PARA_ENTREGA -> novo == StatusPedido.ENTREGUE;
            case ENTREGUE, CANCELADO -> false;
        };

        if (!transicaoValida) {
            throw new ConflictionException("Transição de status inválida: " + atual + " -> " + novo);
        }
    }

    public List<PedidoResponse> listarPedidosCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdOrderByDataCriacaoDesc(clienteId);
        return pedidos.stream()
                .map(PedidoMapper::toResponse)
                .toList();
    }

    public List<PedidoResponse> listarPedidosRestaurante(Long restauranteId) {
        List<Pedido> pedidos = pedidoRepository.findByRestauranteIdOrderByDataCriacaoDesc(restauranteId);
        return pedidos.stream()
                .map(PedidoMapper::toResponse)
                .toList();
    }

    public PedidoResponse buscarPorId(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ConflictionException("Pedido não encontrado."));
        return PedidoMapper.toResponse(pedido);
    }
}