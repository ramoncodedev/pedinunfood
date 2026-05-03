# PedinunFood - Documentação para Desenvolvimento Frontend

## 1. Configurações de Conexão

### Backend API
| Configuração | Valor |
|--------------|-------|
| URL Base | `http://localhost:8080` |
| Protocolo | REST |
| Formato | JSON |

### Banco de Dados (PostgreSQL)
| Configuração | Valor |
|--------------|-------|
| URL | `jdbc:postgresql://localhost:5433/saas_pedidos` |
| Host | `localhost` |
| Porta | `5433` |
| Banco | `saas_pedidos` |
| Usuário | `admin` |
| Senha | `password123` |

### Autenticação JWT
| Configuração | Valor |
|--------------|-------|
| Secret Key | `uma-chave-secreta-muito-longa-para-o-jwt-2024-pedinunfood` |
| Expiração | `86400000ms` (24 horas) |
| Header | `Authorization: Bearer {token}` |

---

## 2. Fluxo de Autenticação

### Registro (`POST /auth/register`)
**Request Body:**
```json
{
  "nome": "string",
  "email": "string",
  "senha": "string (min 6 caracteres)",
  "telefone": "string",
  "cpf": "string",
  "tipo": "CLIENTE | LOGISTA | ENTREGADOR | ADMIN"
}
```

**Response (201 Created):**
```json
{
  "token": "string (JWT)",
  "usuarioId": 0,
  "nome": "string",
  "email": "string",
  "tipo": "CLIENTE",
  "companyId": null
}
```

### Login (`POST /auth/login`)
**Request Body:**
```json
{
  "email": "string",
  "senha": "string"
}
```

**Response (200 OK):**
```json
{
  "token": "string (JWT)",
  "usuarioId": 0,
  "nome": "string",
  "email": "string",
  "tipo": "CLIENTE",
  "companyId": null
}
```

### Dados do Usuário Logado (`GET /auth/me`)
**Headers:** `Authorization: Bearer {token}`

**Response (200 OK):**
```json
{
  "id": 0,
  "nome": "string",
  "email": "string",
  "telefone": "string",
  "cpf": "string",
  "tipo": "CLIENTE",
  "companyId": null,
  "enderecos": [],
  "ativo": true,
  "dataCadastro": "2026-05-03T00:00:00",
  "dataAtualizacao": "2026-05-03T00:00:00"
}
```

---

## 3. Rotas da API

### 3.1. Endpoints Públicos (Sem autenticação)

#### Listar Restaurantes (`GET /companies/restaurantes`)
**Response (200 OK):**
```json
[
  {
    "id": 0,
    "razaoSocial": "string",
    "nomeFantasia": "string",
    "descricao": "string",
    "cnpj": "string",
    "telefone": "string",
    "slug": "string",
    "email": "string",
    "imageUrl": "string",
    "bannerUrl": "string",
    "aberto": true,
    "aprovado": true,
    "ativo": true,
    "taxaEntregaPadrao": 0.00,
    "tempoEntregaEstimado": 0,
    "dataCriacao": "2026-05-03T00:00:00",
    "dataAtualizacao": "2026-05-03T00:00:00"
  }
]
```

#### Listar Categorias (`GET /categorias`)
**Response (200 OK):**
```json
[
  {
    "id": 0,
    "nome": "string",
    "descricao": "string",
    "ativo": true,
    "dataCriacao": "2026-05-03T00:00:00",
    "dataAtualizacao": "2026-05-03T00:00:00"
  }
]
```

#### Listar Produtos (`GET /produtos`)
**Response (200 OK):**
```json
[
  {
    "id": 0,
    "nome": "string",
    "descricao": "string",
    "preco": 0.00,
    "imagemUrl": "string",
    "ativo": true,
    "destaque": false,
    "categoriaId": 0,
    "dataCriacao": "2026-05-03T00:00:00",
    "dataAtualizacao": "2026-05-03T00:00:00"
  }
]
```

---

### 3.2. Endpoints Protegidos (Requerem JWT)

#### Criar Pedido (`POST /pedidos`)
**Headers:** `Authorization: Bearer {token}` (Apenas CLIENTE)

**Request Body:**
```json
{
  "restauranteId": 0,
  "enderecoEntregaId": 0,
  "itens": [
    {
      "produtoId": 0,
      "quantidade": 1,
      "observacao": "string (opcional)"
    }
  ],
  "observacao": "string (opcional)",
  "taxaEntrega": 0.00
}
```

**Response (201 Created):**
```json
{
  "id": 0,
  "clienteId": 0,
  "clienteNome": "string",
  "restauranteId": 0,
  "restauranteNome": "string",
  "status": "AGUARDANDO_CONFIRMACAO",
  "enderecoEntregaId": 0,
  "enderecoEntregaTexto": "string",
  "itens": [
    {
      "id": 0,
      "produtoId": 0,
      "produtoNome": "string",
      "quantidade": 1,
      "precoUnitario": 0.00,
      "subtotal": 0.00,
      "observacao": "string"
    }
  ],
  "subtotal": 0.00,
  "taxaEntrega": 0.00,
  "valorTotal": 0.00,
  "observacao": "string",
  "dataCriacao": "2026-05-03T00:00:00",
  "dataAtualizacao": "2026-05-03T00:00:00"
}
```

#### Listar Pedidos do Cliente (`GET /pedidos/cliente`)
**Headers:** `Authorization: Bearer {token}` (Apenas CLIENTE)

**Response (200 OK):**
```json
[
  {
    "id": 0,
    "clienteId": 0,
    "clienteNome": "string",
    "restauranteId": 0,
    "restauranteNome": "string",
    "status": "AGUARDANDO_CONFIRMACAO",
    "enderecoEntregaId": 0,
    "enderecoEntregaTexto": "string",
    "itens": [],
    "subtotal": 0.00,
    "taxaEntrega": 0.00,
    "valorTotal": 0.00,
    "observacao": "string",
    "dataCriacao": "2026-05-03T00:00:00",
    "dataAtualizacao": "2026-05-03T00:00:00"
  }
]
```

#### Listar Pedidos do Restaurante (`GET /pedidos/restaurante`)
**Headers:** `Authorization: Bearer {token}` (Apenas LOGISTA ou ADMIN)

**Response (200 OK):** Array de pedidos (mesmo formato acima)

#### Buscar Pedido por ID (`GET /pedidos/{id}`)
**Headers:** `Authorization: Bearer {token}`

**Response (200 OK):** Objeto de pedido (mesmo formato acima)

#### Atualizar Status do Pedido (`PUT /pedidos/{id}/status`)
**Headers:** `Authorization: Bearer {token}` (Apenas LOGISTA ou ADMIN)

**Request Body:**
```json
{
  "status": "CONFIRMADO | EM_PREPARACAO | SAIU_PARA_ENTREGA | ENTREGUE | CANCELADO"
}
```

**Response (200 OK):** Objeto de pedido atualizado

#### Cancelar Pedido (`DELETE /pedidos/{id}`)
**Headers:** `Authorization: Bearer {token}` (Apenas CLIENTE)

**Response (200 OK):** Objeto de pedido cancelado

---

### 3.3. Endpoints de Administração (LOGISTA/ADMIN)

#### Criar Restaurante (`POST /companies`)
**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
  "razaoSocial": "string",
  "nomeFantasia": "string",
  "descricao": "string",
  "cnpj": "string",
  "telefone": "string",
  "slug": "string",
  "email": "string (email válido)",
  "imageUrl": "string (opcional)",
  "bannerUrl": "string (opcional)",
  "aberto": true,
  "aprovado": false,
  "ativo": true,
  "taxaEntregaPadrao": 0.00,
  "tempoEntregaEstimado": 0
}
```

**Response (201 Created):** Objeto da company criada

#### Buscar Restaurante por Nome Fantasia (`GET /companies?nomeFantasia={nome}`)
**Headers:** `Authorization: Bearer {token}`

**Response (200 OK):** Objeto da company

#### Criar Categoria (`POST /categorias`)
**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
  "nome": "string",
  "descricao": "string"
}
```

**Response (201 Created):** Objeto da categoria

#### Criar Produto (`POST /produtos`)
**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
  "nome": "string",
  "descricao": "string",
  "preco": 0.00,
  "imagemUrl": "string (opcional)",
  "destaque": false,
  "categoriaId": 0
}
```

**Response (201 Created):** Objeto do produto

---

## 4. Modelos de Dados (Enums)

### Tipos de Usuário (`TipoUsuario`)
```
CLIENTE    - Cliente que faz pedidos
LOGISTA    - Funcionário do restaurante (gestão de pedidos)
ENTREGADOR - Entregador
ADMIN      - Administrador do sistema
```

### Status do Pedido (`StatusPedido`)
```
AGUARDANDO_CONFIRMACAO  - Pedido criado, aguardando confirmação do restaurante
CONFIRMADO               - Pedido confirmado pelo restaurante
EM_PREPARACAO          - Pedido em preparação na cozinha
SAIU_PARA_ENTREGA       - Pedido saiu para entrega
ENTREGUE                - Pedido entregue ao cliente
CANCELADO               - Pedido cancelado
```

---

## 5. Mult-Tenancy

O sistema utiliza **Multi-Tenancy Discriminator** com Hibernate:
- Cada requisição deve incluir o header `X-Tenant-ID` com o ID do restaurante (tenant)
- O `TenantFilter` captura esse header e define o contexto do tenant
- Todas as consultas ao banco são automaticamente filtradas por `tenant_id`
- Para endpoints públicos (`/auth/**`, `/companies/restaurantes`, `/categorias`, `/produtos`), o tenant não é obrigatório

---

## 6. Headers de Requisição

### Para Endpoints Públicos
```http
Content-Type: application/json
```

### Para Endpoints Protegidos
```http
Content-Type: application/json
Authorization: Bearer {seu_jwt_token_aqui}
```

### Para Multi-Tenant (Opcional)
```http
X-Tenant-ID: {id_do_restaurante}
```

---

## 7. Códigos de Status HTTP

| Código | Significado |
|--------|-------------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 400 | Bad Request - Dados inválidos |
| 401 | Unauthorized - Token ausente ou inválido |
| 403 | Forbidden - Sem permissão (role insuficiente) |
| 404 | Not Found - Recurso não encontrado |
| 500 | Internal Server Error - Erro interno |

---

## 8. Exemplo de Fluxo Completo (Frontend)

1. **Usuário acessa a página inicial** → `GET /companies/restaurantes` (público)
2. **Usuário seleciona um restaurante** → `GET /categorias` e `GET /produtos` (público)
3. **Usuário faz login** → `POST /auth/login` → Recebe JWT token
4. **Usuário adiciona itens ao carrinho** (estado local no frontend)
5. **Usuário finaliza pedido** → `POST /pedidos` (com token JWT)
6. **Usuário acompanha pedido** → `GET /pedidos/cliente` (com token JWT)
7. **Restaurante (LOGISTA) vê pedidos** → `GET /pedidos/restaurante` (com token JWT)
8. **Restaurante atualiza status** → `PUT /pedidos/{id}/status` (com token JWT)

---

## 9. Observações Importantes

1. **Tokens JWT**: Armazene no `localStorage` ou `sessionStorage` do navegador
2. **Refresh Token**: Não implementado - token expira em 24h
3. **Validação de formulários**: Todos os DTOs possuem validações (`@NotBlank`, `@Email`, `@Size`, etc.)
4. **Multi-tenant**: A maioria dos endpoints requer o header `X-Tenant-ID`
5. **Roles/Permissões**: 
   - `CLIENTE` pode criar pedidos, ver seus pedidos, cancelar pedidos
   - `LOGISTA` pode ver pedidos do restaurante, atualizar status
   - `ADMIN` acesso total
6. **Imagens**: URLs de imagens são aceitas como strings (upload não implementado)

---

## 10. Tecnologias Recomendadas para o Frontend

- **Framework**: React, Vue.js ou Angular
- **HTTP Client**: Axios ou Fetch API
- **State Management**: Redux (React), Vuex/Pinia (Vue), NgRx (Angular)
- **UI Library**: Material-UI, Ant Design, Tailwind CSS
- **Roteamento**: React Router, Vue Router, Angular Router
- **Formulários**: React Hook Form, VeeValidate, Angular Forms
- **Notificações**: React Toastify, Vue Toastification, Angular Snackbar

---

**Documentação gerada em**: 03/05/2026
**Versão da API**: PedinunFood 0.0.1-SNAPSHOT
**Backend**: Spring Boot 4.0.6 + PostgreSQL 15.17
