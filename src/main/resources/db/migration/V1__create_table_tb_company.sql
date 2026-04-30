CREATE TABLE tb_company (
    id     BIGSERIAL PRIMARY KEY,
    razao_social    VARCHAR(100)  NOT NULL,
    nome_fantasia   VARCHAR(100),
    descricao       VARCHAR(700)  NOT NULL,
    cnpj           VARCHAR(14)    UNIQUE NOT NULL,
    email            VARCHAR(260) UNIQUE NOT NULL,
    telefone        VARCHAR(20)   NOT NULL,
    slug            VARCHAR(100)  UNIQUE NOT NULL,
    image_url       VARCHAR(260),
    banner_url      VARCHAR(260),
    aberto          BOOLEAN  DEFAULT FALSE,
    ativo           BOOLEAN  DEFAULT TRUE,
    aprovado        BOOLEAN  DEFAULT FALSE,
    taxa_entrega_padrao   DECIMAL(10, 2) DEFAULT 0.00,
    tempo_entrega_estimado INTEGER,
    data_criacao          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_company_cnpj  ON tb_company (cnpj);
CREATE UNIQUE INDEX idx_company_email ON tb_company (email);
CREATE UNIQUE INDEX idx_company_slug  ON tb_company (slug);