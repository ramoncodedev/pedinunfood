-- Adicionar tenant_id nas tabelas que ainda não possuem
-- Para multi-tenant discriminator funcionar corretamente

-- 1. tb_company (o próprio tenant, tenant_id aponta para si mesmo ou para um tenant pai)
ALTER TABLE tb_company ADD COLUMN IF NOT EXISTS tenant_id BIGINT;
UPDATE tb_company SET tenant_id = id WHERE tenant_id IS NULL;
ALTER TABLE tb_company ALTER COLUMN tenant_id SET NOT NULL;
CREATE INDEX IF NOT EXISTS idx_company_tenant ON tb_company(tenant_id);

-- 2. tb_usuarios
ALTER TABLE tb_usuarios ADD COLUMN IF NOT EXISTS tenant_id BIGINT;
-- Define tenant_id baseado na company_id (se houver) ou como 0 (sem tenant)
UPDATE tb_usuarios SET tenant_id = COALESCE(company_id, 0) WHERE tenant_id IS NULL;
ALTER TABLE tb_usuarios ALTER COLUMN tenant_id SET NOT NULL;
CREATE INDEX IF NOT EXISTS idx_usuarios_tenant ON tb_usuarios(tenant_id);

-- 3. tb_enderecos
ALTER TABLE tb_enderecos ADD COLUMN IF NOT EXISTS tenant_id BIGINT;
-- Define tenant_id baseado na company_id (se houver) ou como 0
UPDATE tb_enderecos SET tenant_id = COALESCE(company_id, 0) WHERE tenant_id IS NULL;
ALTER TABLE tb_enderecos ALTER COLUMN tenant_id SET NOT NULL;
CREATE INDEX IF NOT EXISTS idx_enderecos_tenant ON tb_enderecos(tenant_id);

-- 4. tb_pedido
ALTER TABLE tb_pedido ADD COLUMN IF NOT EXISTS tenant_id BIGINT;
-- Define tenant_id baseado no restaurante_id
UPDATE tb_pedido SET tenant_id = restaurante_id WHERE tenant_id IS NULL;
ALTER TABLE tb_pedido ALTER COLUMN tenant_id SET NOT NULL;
CREATE INDEX IF NOT EXISTS idx_pedido_tenant ON tb_pedido(tenant_id);

-- 5. tb_item_pedido (precisa join com pedido para obter tenant_id)
ALTER TABLE tb_item_pedido ADD COLUMN IF NOT EXISTS tenant_id BIGINT;
UPDATE tb_item_pedido ip SET tenant_id = p.tenant_id
FROM tb_pedido p WHERE ip.pedido_id = p.id AND ip.tenant_id IS NULL;
ALTER TABLE tb_item_pedido ALTER COLUMN tenant_id SET NOT NULL;
CREATE INDEX IF NOT EXISTS idx_item_pedido_tenant ON tb_item_pedido(tenant_id);
