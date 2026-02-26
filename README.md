# 📘 Teste Prático Fullstack - Documentação Oficial

🔗 **Documentação Online:**  
👉 https://teste-pratico-fullstack-documentati.vercel.app/

---

# 🧾 Sobre o Projeto

Este projeto é uma aplicação **Fullstack** desenvolvida com:

- 🎨 **Frontend:** Vue.js
- ⚙️ **Backend:** Spring Boot
- 🗄️ **Banco de Dados:** PostgreSQL
- 🐳 **Infraestrutura:** Docker e Docker Compose

A aplicação segue boas práticas de arquitetura em camadas, comunicação via API REST e containerização completa.

---

## 🐳 Backend + Banco de Dados (Docker)

Com um único comando, o **Spring Boot** sobe na porta `8080` e o **PostgreSQL** sobe junto:

```bash
docker compose up
```

> Para rodar em background (modo detached):
> ```bash
> docker compose up -d
> ```

> Para parar tudo:
> ```bash
> docker compose down
> ```

> Para parar e remover os volumes (limpar o banco):
> ```bash
> docker compose down -v
> ```

### 🔍 Verificando se está rodando

```bash
# Ver containers ativos
docker compose ps

# Ver logs do backend
docker compose logs -f backend

# Ver logs do banco
docker compose logs -f postgres
```

O backend estará disponível em: **http://localhost:8080**

---

## 💻 Frontend (Vue)

Com o backend já rodando, abra um novo terminal e execute:

```bash
# Entrar na pasta do frontend
cd frontend

# Instalar dependências (apenas na primeira vez)
npm install

# Rodar em modo de desenvolvimento
npm run dev
```

O frontend estará disponível em: **http://localhost:5173** *(ou a porta exibida no terminal)*

> Para build de produção:
> ```bash
> npm run build
> ```

---

## 🧪 Testes

### Spring Boot (Backend)

**Rodando via Docker:**
```bash
docker compose run --rm backend ./mvnw test
```

**Rodando localmente (sem Docker):**
```bash
# Na raiz do projeto ou pasta /backend
./mvnw test
```

> Para rodar um teste específico:
> ```bash
> ./mvnw test -Dtest=NomeDaClasseTest
> ```

> Para rodar e ver o relatório:
> ```bash
> ./mvnw test surefire-report:report
# Relatório gerado em: target/site/surefire-report.html
> ```

---

### Vue (Frontend)

```bash
# Entrar na pasta do frontend
cd frontend

# Rodar testes unitários (Vitest)
npm run test

# Rodar testes em modo watch (re-executa ao salvar)
npm run test:watch

# Rodar testes com cobertura
npm run test:coverage
```

> Para testes E2E com Cypress ou Playwright (se configurado):
> ```bash
> npm run test:e2e
> ```

---


## 🌐 Portas utilizadas

| Serviço    | Porta  |
|------------|--------|
| Backend    | 8080   |
| PostgreSQL | 5432   |
| Frontend   | 5173   |
