
CREATE TABLE tb_categoria (
         id BIGSERIAL PRIMARY KEY,
         tenant_id BIGINT NOT NULL, -- Coluna de isolamento
         nome VARCHAR(50) NOT NULL,
         descricao TEXT,
         ativo BOOLEAN DEFAULT TRUE,
         data_criacao TIMESTAMP WITHOUT TIME ZONE,
         data_atualizacao TIMESTAMP WITHOUT TIME ZONE
);

-- Índice busca rápida por restaurante
CREATE INDEX idx_categoria_tenant ON tb_categoria(tenant_id);

-- FK garantir que o tenant_id aponte para uma empresa real
ALTER TABLE tb_categoria
    ADD CONSTRAINT fk_categoria_company
        FOREIGN KEY (tenant_id) REFERENCES tb_company(id);