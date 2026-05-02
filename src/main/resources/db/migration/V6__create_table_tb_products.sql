CREATE TABLE tb_produto (
         id BIGSERIAL PRIMARY KEY,
         tenant_id BIGINT NOT NULL, -- O nosso identificador do multi-tenancy
         nome VARCHAR(100) NOT NULL,
         descricao VARCHAR(500) NOT NULL,
         preco DECIMAL(10, 2) NOT NULL,
         imagem_url VARCHAR(255),
         ativo BOOLEAN DEFAULT TRUE,
         destaque BOOLEAN DEFAULT FALSE,
         categoria_id BIGINT,
         data_criacao TIMESTAMP WITHOUT TIME ZONE,
         data_atualizacao TIMESTAMP WITHOUT TIME ZONE,


        CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES tb_categoria(id),


        CONSTRAINT fk_produto_company FOREIGN KEY (tenant_id) REFERENCES tb_company(id)
);

-- ÍNDICE CRUCIAL: Como toda query terá "WHERE tenant_id = ?",
-- esse índice evita que o banco fique lento.
CREATE INDEX idx_produto_tenant ON tb_produto(tenant_id);