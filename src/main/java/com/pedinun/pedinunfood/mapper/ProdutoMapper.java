package com.pedinun.pedinunfood.mapper;

import com.pedinun.pedinunfood.dto.request.ProdutoRequest;
import com.pedinun.pedinunfood.dto.response.ProdutoResponse;
import com.pedinun.pedinunfood.entity.produto.Produto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest request) {
        return Produto.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .preco(request.preco())
                .imagemUrl(request.imagemUrl())
                .ativo(request.ativo())
                .destaque(request.destaque())
                .build();
    }

public ProdutoResponse toResponse(Produto produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .imagemUrl(produto.getImagemUrl())
                .ativo(produto.getAtivo())
                .destaque(produto.getDestaque())
                .categoriaId(produto.getCategoria() != null ? produto.getCategoria().getId() : null)
                .categoriaNome(produto.getCategoria() != null ? produto.getCategoria().getNome() : null)
                .dataCriacao(produto.getDataCriacao())
                .dataAtualizacao(produto.getDataAtualizacao())
                .build();
    }
}