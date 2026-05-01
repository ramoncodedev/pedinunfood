package com.pedinun.pedinunfood.service;

import com.pedinun.pedinunfood.entity.company.Company;
import com.pedinun.pedinunfood.exception.ConflictionException;
import com.pedinun.pedinunfood.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company saveCompany(Company company) {
        verificarCnpj(company.getCnpj());
        verificarEmail(company.getEmail());
        verificarSlug(company.getSlug());

        return companyRepository.save(company);
    }

    private void verificarCnpj(String cnpj) {
        if (companyRepository.existsByCnpj(cnpj)) {
            throw new ConflictionException("O CNPJ já está em uso.");
        }
    }

    private void verificarEmail(String email) {
        if (companyRepository.existsByEmail(email)) {
            throw new ConflictionException("O email já está em uso.");
        }
    }

    private void verificarSlug(String slug) {
        if (companyRepository.existsBySlug(slug)) {
            throw new ConflictionException("O slug já está em uso.");
        }
    }
}