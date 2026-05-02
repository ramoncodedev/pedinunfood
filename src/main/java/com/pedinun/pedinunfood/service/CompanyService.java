package com.pedinun.pedinunfood.service;

import com.pedinun.pedinunfood.entity.company.Company;
import com.pedinun.pedinunfood.exception.ConflictionException;
import com.pedinun.pedinunfood.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company saveCompany(Company company) {
        verificarCnpj(company.getCnpj());
        verificarEmail(company.getEmail());

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

    public List<Company> findAll(){

        List<Company> companies = companyRepository.findAll();

        if (companies.isEmpty()) {
            throw new IllegalStateException("Não existem restaurante cadastradas.");
        }

        return companies;

    }


    public Company findByNome(String nomeFantasia){
        verificarNome(nomeFantasia);
        return companyRepository.findByNomeFantasia(nomeFantasia);
    }


    private void verificarNome(String nomeFantasia){
        if (!companyRepository.existsByNomeFantasia(nomeFantasia)) {
            throw new ConflictionException("não existi nenhum restaurante com esse nome.");
        }
    }
}