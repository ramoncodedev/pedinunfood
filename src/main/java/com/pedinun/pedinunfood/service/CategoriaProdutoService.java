package com.pedinun.pedinunfood.service;

import com.pedinun.pedinunfood.entity.produto.CategoriaProduto;
import com.pedinun.pedinunfood.exception.ConflictionException;
import com.pedinun.pedinunfood.repository.produto.CategoriaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaProdutoService {

    private final CategoriaProdutoRepository repository;

    public CategoriaProduto save(CategoriaProduto entity) {
        verificarNome(entity.getNome());
        return repository.save(entity);
    }

    public List<CategoriaProduto> findAll() {
        List<CategoriaProduto> categorias = repository.findAll();
        if (categorias.isEmpty()) {
            throw new IllegalStateException("Não existem categorias cadastradas.");
        }
        return categorias;
    }

    public CategoriaProduto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ConflictionException("Categoria não encontrada."));
    }

    public CategoriaProduto update(Long id, CategoriaProduto entity) {
        CategoriaProduto existente = findById(id);
        existente.setNome(entity.getNome());
        existente.setDescricao(entity.getDescricao());
        existente.setAtivo(entity.getAtivo());
        return repository.save(existente);
    }

    public void delete(Long id) {
        CategoriaProduto existente = findById(id);
        repository.delete(existente);
    }

    private void verificarNome(String nome) {
        if (repository.existsByNome(nome)) {
            throw new ConflictionException("O nome da categoria já está em uso.");
        }
    }
}