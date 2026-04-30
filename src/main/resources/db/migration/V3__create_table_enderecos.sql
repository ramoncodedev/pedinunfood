CREATE TABLE tb_enderecos (
    id    BIGSERIAL    PRIMARY KEY,
    cep   VARCHAR(9)   NOT NULL,
    logradouro  VARCHAR(100) NOT NULL,
    numero    VARCHAR(20)  NOT NULL,
    complemento   VARCHAR(100) NOT NULL,
    bairro    VARCHAR(60)  NOT NULL,
    cidade    VARCHAR(60)  NOT NULL,
    uf    VARCHAR(2)   NOT NULL,
    ponto_referencia VARCHAR(150) NOT NULL,
    endereco_principal BOOLEAN      DEFAULT FALSE NOT NULL,
    usuario_id   BIGINT       NOT NULL,
    company_id    BIGINT       NOT NULL,
    data_cadastro   TIMESTAMP    NOT NULL,

    CONSTRAINT fk_endereco_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuarios (id) ON DELETE CASCADE,
    CONSTRAINT fk_endereco_company FOREIGN KEY (company_id) REFERENCES tb_company  (id) ON DELETE CASCADE
);
