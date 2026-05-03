package com.pedinun.pedinunfood.repository.pedido;

import com.pedinun.pedinunfood.entity.pedido.Pedido;
import com.pedinun.pedinunfood.entity.pedido.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByRestauranteId(Long restauranteId);

    List<Pedido> findByRestauranteIdAndStatus(Long restauranteId, StatusPedido status);

    List<Pedido> findByClienteIdOrderByDataCriacaoDesc(Long clienteId);

    List<Pedido> findByRestauranteIdOrderByDataCriacaoDesc(Long restauranteId);
}