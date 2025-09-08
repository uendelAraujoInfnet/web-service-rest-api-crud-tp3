# Empresa API

API REST para cadastro de funcionários, produtos, fornecedores, clientes e departamentos.

## Tecnologias
- Java 21
- Spring Boot 3.5.5
- Spring Data JPA (Hibernate)
- H2 (in-memory)
- Lombok
- JUnit, MockMvc

## Como gerar o projeto
Gerado via start.spring.io (Java 21, Spring Boot 3.5.5) com dependências: Web, JPA, H2, Validation, Lombok.

## Executando
1. `mvn clean install`
2. `mvn spring-boot:run`
A API estará em `http://localhost:8080`.

## Endpoints principais
- `GET /api/employees` — lista
- `POST /api/employees` — cria
- `GET /api/employees/{id}` — busca
- `PUT /api/employees/{id}` — atualiza
- `DELETE /api/employees/{id}` — exclui

(Repita para /products, /suppliers, /customers, /departments)

## Testes
`mvn test`

## Observações
- Validação com Jakarta Bean Validation; erros retornam 400 com detalhamento.
- Se recurso inexistente → 404.
- POST → 201 Created + Location header.
