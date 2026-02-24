# 🏭 Production Optimizer — Back-end


## Pré-requisitos

- Java 17+, Maven 3.8+, Docker 24+

---

## 1. Criar projeto no [start.spring.io](https://start.spring.io)


**Dependências:**
`Spring Web` · `Spring Data JPA` · `PostgreSQL Driver` · `Lombok` · `Validation` · `DevTools`

---

## 2. Subir PostgreSQL com Docker

Crie `docker-compose.yml` na raiz:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:16
    container_name: factory_postgres
    environment:
      POSTGRES_DB: factorydb
      POSTGRES_USER: factory_user
      POSTGRES_PASSWORD: factory_pass
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
volumes:
  postgres_data:
```

```bash
docker compose up -d
```

---

## 3. Configurar `application.properties`

```properties
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/factorydb
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=factory_user
spring.datasource.password=factory_pass

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 4. Rodar o projeto

```bash
docker compose up -d
mvn spring-boot:run
```

---

## 5. Testes unitários

```bash
mvn test
```

---

## 6. Testes de API com Postman

### Configuração inicial

1. Abra o Postman e crie uma **Collection** chamada `Production Optimizer`
2. Crie um **Environment** `Local` com a variável `base_url = http://localhost:8080`
3. Use `{{base_url}}` em todas as requisições abaixo

---

### Matérias-Primas

**POST** `{{base_url}}/api/raw-materials`
```json
{
  "code": "MP001",
  "name": "Farinha de Trigo",
  "stockQuantity": 500.0
}
```
Resposta esperada: `201 Created`

---

**GET** `{{base_url}}/api/raw-materials`
Resposta esperada: `200 OK` — array com todos os registros

---

**GET** `{{base_url}}/api/raw-materials/1`
Resposta esperada: `200 OK` — objeto da matéria-prima

---

**PUT** `{{base_url}}/api/raw-materials/1`
```json
{
  "code": "MP001",
  "name": "Farinha Integral",
  "stockQuantity": 300.0
}
```
Resposta esperada: `200 OK`

---

**DELETE** `{{base_url}}/api/raw-materials/1`
Resposta esperada: `204 No Content`

---

### Produtos

**POST** `{{base_url}}/api/products`
```json
{
  "code": "PRD001",
  "name": "Pao Frances",
  "price": 0.75,
  "ingredients": [
    { "rawMaterialId": 1, "requiredQuantity": 50.0 },
    { "rawMaterialId": 2, "requiredQuantity": 10.0 }
  ]
}
```
Resposta esperada: `201 Created`

---

**GET** `{{base_url}}/api/products`

**PUT** `{{base_url}}/api/products/1` — mesmo body do POST com alterações

**DELETE** `{{base_url}}/api/products/1` — `204 No Content`

---

### Otimizacao de Producao

**GET** `{{base_url}}/api/production/optimize`
```json
{
  "suggestions": [
    {
      "productName": "Bolo de Chocolate",
      "quantityToProduce": 3,
      "totalRevenue": 45.00
    },
    {
      "productName": "Pao Frances",
      "quantityToProduce": 10,
      "totalRevenue": 7.50
    }
  ],
  "totalExpectedRevenue": 52.50
}
```

---

### Cenarios de erro

| Cenario | Request | Esperado |
|---|---|---|
| ID inexistente | `GET /api/products/999` | `404 Not Found` |
| Body invalido | `POST /api/products` sem `name` | `400 Bad Request` |
| Sem estoque | `GET /api/production/optimize` | `200 OK` lista vazia |

---

### Script de validacao automatica (aba Tests do Postman)

```javascript
pm.test("Status correto", () => {
    pm.response.to.have.status(200);
});

pm.test("Retornou lista", () => {
    pm.expect(pm.response.json()).to.be.an("array");
});

// Salvar ID para proxima requisicao
const body = pm.response.json();
pm.environment.set("product_id", body.id);
```
