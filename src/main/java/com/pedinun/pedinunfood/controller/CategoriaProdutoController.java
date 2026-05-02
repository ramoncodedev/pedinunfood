package com.pedinun.pedinunfood.controller;

import com.pedinun.pedinunfood.dto.request.CategoriaProdutoRequest;
import com.pedinun.pedinunfood.dto.response.CategoriaProdutoResponse;
import com.pedinun.pedinunfood.entity.produto.CategoriaProduto;
import com.pedinun.pedinunfood.mapper.CategoriaProdutoMapper;
import com.pedinun.pedinunfood.service.CategoriaProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaProdutoController {

    private final CategoriaProdutoService service;

    @PostMapping
    public ResponseEntity<CategoriaProdutoResponse> criar(@RequestBody CategoriaProdutoRequest request) {
        CategoriaProduto entity = CategoriaProdutoMapper.toEntity(request);
        CategoriaProduto salvo = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaProdutoMapper.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponse>> findAll() {
        List<CategoriaProduto> categorias = service.findAll();
        List<CategoriaProdutoResponse> responses = categorias.stream()
                .map(CategoriaProdutoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponse> findById(@PathVariable Long id) {
        CategoriaProduto categoria = service.findById(id);
        return ResponseEntity.ok(CategoriaProdutoMapper.toResponse(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponse> update(@PathVariable Long id, @RequestBody CategoriaProdutoRequest request) {
        CategoriaProduto entity = CategoriaProdutoMapper.toEntity(request);
        CategoriaProduto atualizado = service.update(id, entity);
        return ResponseEntity.ok(CategoriaProdutoMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}