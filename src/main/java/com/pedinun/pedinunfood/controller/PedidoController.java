package com.pedinun.pedinunfood.controller;

import com.pedinun.pedinunfood.dto.request.AtualizarStatusPedidoRequest;
import com.pedinun.pedinunfood.dto.request.PedidoRequest;
import com.pedinun.pedinunfood.dto.response.PedidoResponse;
import com.pedinun.pedinunfood.security.UsuarioPrincipal;
import com.pedinun.pedinunfood.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(
            @Valid @RequestBody PedidoRequest request,
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        if (!principal.isCliente()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PedidoResponse response = pedidoService.criarPedido(request, principal.getUsuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<PedidoResponse>> listarPedidosCliente(
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        if (!principal.isCliente()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<PedidoResponse> pedidos = pedidoService.listarPedidosCliente(principal.getUsuarioId());
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/restaurante")
    public ResponseEntity<List<PedidoResponse>> listarPedidosRestaurante(
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        if (!principal.isLogista() && !principal.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Long restauranteId = principal.getCompanyId();
        if (restauranteId == null) {
            return ResponseEntity.badRequest().build();
        }
        List<PedidoResponse> pedidos = pedidoService.listarPedidosRestaurante(restauranteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        PedidoResponse pedido = pedidoService.buscarPorId(id);

        boolean temAcesso = pedido.clienteId().equals(principal.getUsuarioId())
                || (principal.getCompanyId() != null && pedido.restauranteId().equals(principal.getCompanyId()));

        if (!temAcesso && !principal.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusPedidoRequest request,
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        if (!principal.isLogista() && !principal.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PedidoResponse response = pedidoService.atualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PedidoResponse> cancelarPedido(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioPrincipal principal) {
        if (!principal.isCliente()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PedidoResponse response = pedidoService.cancelarPedido(id, principal.getUsuarioId());
        return ResponseEntity.ok(response);
    }
}