package com.pedinun.pedinunfood.repository.company;

import com.pedinun.pedinunfood.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByNome(String nome);

    boolean existsByCnpj(String cnpj);

    boolean existsByName(String nome);

    Company findByEmail(String email);

    boolean existsByEmail(String email);
}