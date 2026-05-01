package com.pedinun.pedinunfood.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CompanyResponse(
        Long id,
        String razaoSocial,
        String nomeFantasia,
        String descricao,
        String cnpj,
        String telefone,
        String slug,
        String email,
        String imageUrl,
        String bannerUrl,
        Boolean aberto,
        Boolean aprovado,
        Boolean ativo,
        BigDecimal taxaEntregaPadrao,
        Integer tempoEntregaEstimado,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}