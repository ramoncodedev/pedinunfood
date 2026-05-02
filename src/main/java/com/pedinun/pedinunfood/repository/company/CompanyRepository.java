package com.pedinun.pedinunfood.repository.company;

import com.pedinun.pedinunfood.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByNomeFantasia(String nomeFantasia);

    boolean existsByCnpj(String cnpj);

    boolean existsByNomeFantasia(String nomeFantasia);

    Company findByEmail(String email);

    boolean existsByEmail(String email);
}