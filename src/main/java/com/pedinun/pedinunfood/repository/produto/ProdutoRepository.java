package com.pedinun.pedinunfood.repository.produto;


import com.pedinun.pedinunfood.entity.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
