-- Tabela de Pedidos
CREATE TABLE tb_pedido (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES tb_usuarios(id),
    restaurante_id BIGINT NOT NULL REFERENCES tb_company(id),
    status VARCHAR(30) NOT NULL DEFAULT 'AGUARDANDO_CONFIRMACAO',
    endereco_entrega_id BIGINT NOT NULL REFERENCES tb_enderecos(id),
    subtotal DECIMAL(10, 2) NOT NULL DEFAULT 0,
    taxa_entrega DECIMAL(10, 2) NOT NULL DEFAULT 0,
    valor_total DECIMAL(10, 2) NOT NULL DEFAULT 0,
    observacao VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Itens do Pedido
CREATE TABLE tb_item_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES tb_pedido(id) ON DELETE CASCADE,
    produto_id BIGINT NOT NULL REFERENCES tb_produto(id),
    quantidade INTEGER NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    observacao VARCHAR(200)
);

-- Índices para performance
CREATE INDEX idx_pedido_cliente_id ON tb_pedido(cliente_id);
CREATE INDEX idx_pedido_restaurante_id ON tb_pedido(restaurante_id);
CREATE INDEX idx_pedido_status ON tb_pedido(status);
CREATE INDEX idx_item_pedido_pedido_id ON tb_item_pedido(pedido_id);