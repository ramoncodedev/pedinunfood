package com.pedinun.pedinunfood.mapper;

import com.pedinun.pedinunfood.dto.request.CategoriaProdutoRequest;
import com.pedinun.pedinunfood.dto.response.CategoriaProdutoResponse;
import com.pedinun.pedinunfood.entity.produto.CategoriaProduto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoriaProdutoMapper {

    public CategoriaProduto toEntity(CategoriaProdutoRequest request) {
        return CategoriaProduto.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .ativo(request.ativo())
                .build();
    }

    public CategoriaProdutoResponse toResponse(CategoriaProduto categoria) {
        return CategoriaProdutoResponse.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .ativo(categoria.getAtivo())
                .dataCriacao(categoria.getDataCriacao())
                .dataAtualizacao(categoria.getDataAtualizacao())
                .build();
    }
}