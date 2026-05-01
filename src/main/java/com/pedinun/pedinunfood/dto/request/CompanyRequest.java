package com.pedinun.pedinunfood.dto.request;

import jakarta.validation.constraints.Email;

public record CompanyRequest(
        String razaoSocial,
        String nomeFantasia,
        String descricao,
        String cnpj,
        String telefone,
        String slug,
        @Email(message = "O email deve ser válido")
        String email,
        String imageUrl,
        String bannerUrl,
        Boolean aberto,
        Boolean aprovado,
        Boolean ativo,
        java.math.BigDecimal taxaEntregaPadrao,
        Integer tempoEntregaEstimado
) {
}