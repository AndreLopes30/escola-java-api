# 🏫 Escola API

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Tests](https://img.shields.io/badge/tests-JUnit%20%7C%20MockMvc%20%7C%20Testcontainers-brightgreen?style=for-the-badge)

API REST de gerenciamento escolar desenvolvida em **Java 21 e Spring Boot**, com autenticação stateless via JWT, persistência em PostgreSQL, arquitetura em camadas e testes automatizados em diferentes níveis.

---

## 🚀 Funcionalidades

- autenticação de usuários;
- geração e validação de JWT;
- cadastro e gerenciamento de alunos;
- validação dos dados de entrada;
- persistência em PostgreSQL;
- tratamento adequado de respostas HTTP;
- documentação da API com OpenAPI / Swagger.

---

## 🏗️ Arquitetura

O projeto segue uma organização em camadas para separar responsabilidades entre:

- **Controller** — exposição dos endpoints HTTP;
- **Service** — regras de negócio;
- **Repository** — comunicação com a camada de persistência;
- **Entity / Model** — representação das entidades;
- **DTO** — transferência de dados entre as diferentes camadas;
- **Security** — autenticação e autorização;
- **Configuration** — configurações da aplicação.

Essa separação facilita manutenção, testes e evolução do sistema.

---

## 🔐 Autenticação

A API utiliza autenticação **stateless** com JSON Web Token.

Fluxo básico:

1. o usuário envia suas credenciais;
2. a aplicação valida os dados;
3. um JWT é gerado;
4. o cliente utiliza o token nas próximas requisições protegidas;
5. o backend valida o token antes de liberar o acesso ao recurso.

Exemplo:

```http
Authorization: Bearer <token>
