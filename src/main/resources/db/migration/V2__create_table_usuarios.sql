CREATE TABLE tb_usuarios (
    id               BIGSERIAL    PRIMARY KEY,
    nome    VARCHAR(100) NOT NULL,
    email    VARCHAR(260) UNIQUE NOT NULL,
    senha    VARCHAR(260) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL,
    cpf    VARCHAR(14)  UNIQUE NOT NULL,
    tipo_usuario   VARCHAR(20)  NOT NULL,
    ativo      BOOLEAN      DEFAULT TRUE,
    company_id   BIGINT,
    data_cadastro  TIMESTAMP    NOT NULL,
    data_atualizacao TIMESTAMP,

    CONSTRAINT fk_usuario_company FOREIGN KEY (company_id) REFERENCES tb_company (id) ON DELETE SET NULL
);

CREATE INDEX idx_usuario_company ON tb_usuarios (company_id);
