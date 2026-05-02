package com.pedinun.pedinunfood.service;

import com.pedinun.pedinunfood.entity.produto.CategoriaProduto;
import com.pedinun.pedinunfood.entity.produto.Produto;
import com.pedinun.pedinunfood.exception.ConflictionException;
import com.pedinun.pedinunfood.repository.produto.CategoriaProdutoRepository;
import com.pedinun.pedinunfood.repository.produto.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaProdutoRepository categoriaRepository;

    public Produto save(Produto entity, Long categoriaId) {
        if (categoriaId != null) {
            CategoriaProduto categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ConflictionException("Categoria não encontrada."));
            entity.setCategoria(categoria);
        }

        return repository.save(entity);
    }

    public List<Produto> findAll() {
        List<Produto> produtos = repository.findAll();
        if (produtos.isEmpty()) {
            throw new IllegalStateException("Não existem produtos cadastrados.");
        }
        return produtos;
    }

    public Produto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ConflictionException("Produto não encontrado."));
    }

    public Produto update(Long id, Produto entity, Long categoriaId) {
        Produto existente = findById(id);

        if (categoriaId != null) {
            CategoriaProduto categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ConflictionException("Categoria não encontrada."));
            existente.setCategoria(categoria);
        }

        existente.setNome(entity.getNome());
        existente.setDescricao(entity.getDescricao());
        existente.setPreco(entity.getPreco());
        existente.setImagemUrl(entity.getImagemUrl());
        existente.setAtivo(entity.getAtivo());
        existente.setDestaque(entity.getDestaque());

        return repository.save(existente);
    }

    public void delete(Long id) {
        Produto existente = findById(id);
        repository.delete(existente);
    }
}