package com.pedinun.pedinunfood.controller;

import com.pedinun.pedinunfood.dto.request.ProdutoRequest;
import com.pedinun.pedinunfood.dto.response.ProdutoResponse;
import com.pedinun.pedinunfood.entity.produto.Produto;
import com.pedinun.pedinunfood.mapper.ProdutoMapper;
import com.pedinun.pedinunfood.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest request) {
        Produto entity = ProdutoMapper.toEntity(request);
        Produto salvo = service.save(entity, request.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoMapper.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> findAll() {
        List<Produto> produtos = service.findAll();
        List<ProdutoResponse> responses = produtos.stream()
                .map(ProdutoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        Produto produto = service.findById(id);
        return ResponseEntity.ok(ProdutoMapper.toResponse(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        Produto entity = ProdutoMapper.toEntity(request);
        Produto atualizado = service.update(id, entity, request.categoriaId());
        return ResponseEntity.ok(ProdutoMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}