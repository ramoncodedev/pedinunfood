-- SEED DATA: Categorias e Produtos de Teste
-- Depende da V9 (colunas tenant_id criadas)

DO $$
DECLARE
    v_tenant_id BIGINT;
    v_cat_lanches BIGINT;
    v_cat_bebidas BIGINT;
    v_cat_sobremesas BIGINT;
BEGIN
    -- Usar o primeiro restaurante cadastrado como tenant de teste
    SELECT id INTO v_tenant_id FROM tb_company WHERE ativo = true LIMIT 1;

    IF v_tenant_id IS NOT NULL THEN
        -- Inserir Categorias (com tenant_id)
        INSERT INTO tb_categoria (tenant_id, nome, descricao, ativo, data_criacao, data_atualizacao)
        VALUES (v_tenant_id, 'Lanches', 'Hambúrgueres e sanduíches artesanais', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        RETURNING id INTO v_cat_lanches;

        INSERT INTO tb_categoria (tenant_id, nome, descricao, ativo, data_criacao, data_atualizacao)
        VALUES (v_tenant_id, 'Bebidas', 'Refrigerantes, sucos e águas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        RETURNING id INTO v_cat_bebidas;

        INSERT INTO tb_categoria (tenant_id, nome, descricao, ativo, data_criacao, data_atualizacao)
        VALUES (v_tenant_id, 'Sobremesas', 'Doces e sobremesas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        RETURNING id INTO v_cat_sobremesas;

        -- Inserir Produtos (com tenant_id)
        INSERT INTO tb_produto (tenant_id, nome, descricao, preco, ativo, destaque, categoria_id, data_criacao, data_atualizacao)
        VALUES
        (v_tenant_id, 'X-Burger', 'Hambúrguer artesanal com queijo', 25.90, true, true, v_cat_lanches, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
        (v_tenant_id, 'X-Salada', 'Hambúrguer com alface e tomate', 27.90, true, false, v_cat_lanches, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
        (v_tenant_id, 'X-Bacon', 'Hambúrguer com bacon crocante', 29.90, true, true, v_cat_lanches, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
        (v_tenant_id, 'Coca-Cola', 'Refrigerante 350ml', 6.00, true, false, v_cat_bebidas, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
        (v_tenant_id, 'Suco Natural', 'Suco de laranja 500ml', 8.50, true, false, v_cat_bebidas, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
        (v_tenant_id, 'Pudim', 'Pudim de leite condensado', 12.00, true, false, v_cat_sobremesas, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    END IF;
END $$;
