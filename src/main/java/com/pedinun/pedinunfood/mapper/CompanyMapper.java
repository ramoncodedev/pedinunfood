package com.pedinun.pedinunfood.mapper;

import com.pedinun.pedinunfood.dto.request.CompanyRequest;
import com.pedinun.pedinunfood.dto.response.CompanyResponse;
import com.pedinun.pedinunfood.entity.company.Company;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CompanyMapper {

    public Company toEntity(CompanyRequest companyRequest) {
        return Company.builder()
                .razaoSocial(companyRequest.razaoSocial())
                .nomeFantasia(companyRequest.nomeFantasia())
                .descricao(companyRequest.descricao())
                .cnpj(companyRequest.cnpj())
                .telefone(companyRequest.telefone())
                .slug(companyRequest.slug())
                .email(companyRequest.email())
                .imageUrl(companyRequest.imageUrl())
                .bannerUrl(companyRequest.bannerUrl())
                .aberto(companyRequest.aberto())
                .aprovado(companyRequest.aprovado())
                .ativo(companyRequest.ativo())
                .taxaEntregaPadrao(companyRequest.taxaEntregaPadrao())
                .tempoEntregaEstimado(companyRequest.tempoEntregaEstimado())
                .build();
    }

    public CompanyResponse toResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .razaoSocial(company.getRazaoSocial())
                .nomeFantasia(company.getNomeFantasia())
                .descricao(company.getDescricao())
                .cnpj(company.getCnpj())
                .telefone(company.getTelefone())
                .slug(company.getSlug())
                .email(company.getEmail())
                .imageUrl(company.getImageUrl())
                .bannerUrl(company.getBannerUrl())
                .aberto(company.getAberto())
                .aprovado(company.getAprovado())
                .ativo(company.getAtivo())
                .taxaEntregaPadrao(company.getTaxaEntregaPadrao())
                .tempoEntregaEstimado(company.getTempoEntregaEstimado())
                .dataCriacao(company.getDataCriacao())
                .dataAtualizacao(company.getDataAtualizacao())
                .build();
    }
}