package com.pedinun.pedinunfood.repository.produto;

import com.pedinun.pedinunfood.entity.produto.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
    boolean existsByNome(String nome);
}