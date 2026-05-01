package com.pedinun.pedinunfood.repository.company;

import com.pedinun.pedinunfood.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    Company findBySlug(String slug);

    boolean existsBySlug(String slug);

    Company findByEmail(String email);

    boolean existsByEmail(String email);
}