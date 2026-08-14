# Escola API

[![CI](https://github.com/AndreLopes30/escola-java-api/actions/workflows/ci.yml/badge.svg)](https://github.com/AndreLopes30/escola-java-api/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Tests](https://img.shields.io/badge/tests-JUnit%20%7C%20MockMvc%20%7C%20Testcontainers-brightgreen?style=for-the-badge)

API REST para cadastro de usuários e gerenciamento de alunos. O projeto demonstra uma aplicação Spring Boot em camadas, persistência com JPA/PostgreSQL, validação de entrada e autenticação stateless com JWT.

## Stack

- Java 21 e Maven Wrapper
- Spring Boot 3.5.14
- Spring Web, Spring Data JPA, Spring Security e Bean Validation
- PostgreSQL
- Auth0 Java JWT e BCrypt
- Springdoc OpenAPI/Swagger UI
- JUnit 5, Mockito, MockMvc e Testcontainers

## Funcionalidades

- Cadastro de usuário com senha armazenada como hash BCrypt
- Login com emissão de JWT
- CRUD de alunos em rotas protegidas
- Validação declarativa dos dados de alunos e usuários
- Resposta estruturada para erros de validação
- Contrato OpenAPI e interface Swagger UI

## Arquitetura

O código está organizado em `controller`, `service`, `repository`, `model`, `dto`, `config` e `exception`.

```text
HTTP -> Controller -> Service -> Repository -> PostgreSQL
           |             |
          DTO       regra de nota
```

Os controllers definem o contrato HTTP; `AlunoService` concentra a validação de nota e as operações do domínio; os repositórios Spring Data JPA cuidam da persistência. DTOs separam a entrada e a saída usadas no cadastro de alunos.

## Autenticação JWT

1. Cadastre um usuário em `POST /usuarios`.
2. Envie login e senha para `POST /login`.
3. Use o token retornado nas rotas de alunos:

```http
Authorization: Bearer <token>
```

O `SecurityFilter` valida o token, recupera o usuário pelo login e preenche o contexto do Spring Security. A aplicação não cria sessão no servidor. Os tokens são emitidos pelo `TokenService` com issuer `API Escola` e expiração de duas horas.

`POST /usuarios`, `POST /login` e os recursos do Swagger são públicos. As demais rotas exigem autenticação.

## Endpoints

| Método | Rota | Autenticação | Resultado |
| --- | --- | --- | --- |
| `POST` | `/usuarios` | Pública | Cadastra um usuário |
| `POST` | `/login` | Pública | Autentica e retorna `{ "token": "... " }` |
| `GET` | `/alunos` | JWT | Lista alunos |
| `GET` | `/alunos/{id}` | JWT | Busca um aluno; retorna 404 quando ausente |
| `POST` | `/alunos` | JWT | Cadastra um aluno e retorna 201 |
| `PUT` | `/alunos/{id}` | JWT | Atualiza um aluno |
| `DELETE` | `/alunos/{id}` | JWT | Exclui um aluno e retorna 204 |

### Exemplos

Cadastro e login:

```http
POST /usuarios
Content-Type: application/json

{
  "login": "andre",
  "senha": "uma-senha-segura"
}
```

```http
POST /login
Content-Type: application/json

{
  "login": "andre",
  "senha": "uma-senha-segura"
}
```

Cadastro de aluno:

```http
POST /alunos
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Maria Silva",
  "nota": 8.5,
  "turma": "A",
  "idade": 16
}
```

## Modelo de dados e validações

### Usuário (`usuarios`)

- `id`: identificador gerado pelo banco
- `login`: obrigatório e único
- `senha`: obrigatória e persistida como hash BCrypt

### Aluno (`tb_alunos`)

- `id`: identificador gerado pelo banco
- `nome`: obrigatório, não vazio e limitado a 100 caracteres na coluna
- `nota`: obrigatória, entre 0 e 10
- `turma`: opcional
- `idade`: opcional e não negativa

Entradas inválidas produzem HTTP 400 com timestamp, status, mensagem e erros por campo por meio de `GlobalExceptionHandler`.

## OpenAPI e Swagger

Com a aplicação em execução:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Documento OpenAPI: `http://localhost:8080/v3/api-docs`

Essas rotas são públicas para permitir a exploração e autenticação pela interface.

## Configuração

As propriedades aceitam variáveis de ambiente, com valores locais padrão definidos em `application.properties`:

| Variável | Finalidade | Padrão local |
| --- | --- | --- |
| `DB_URL` | URL JDBC do PostgreSQL | `jdbc:postgresql://localhost:5432/escola_api` |
| `DB_USERNAME` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `SUA_SENHA_AQUI` |
| `JWT_SECRET` | Segredo de assinatura dos tokens | valor apenas para desenvolvimento |

Forneça valores próprios fora do código em ambientes compartilhados ou de produção. O Hibernate está configurado com `ddl-auto=update`; use uma estratégia de migrations antes de operar o projeto em produção.

## Execução local

Pré-requisitos: JDK 21 e PostgreSQL. Docker também é necessário para executar o teste de integração com Testcontainers.

```bash
git clone https://github.com/AndreLopes30/escola-java-api.git
cd escola-java-api

# Linux/macOS
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

A API usa `http://localhost:8080` por padrão.

## Testes

```bash
# Linux/macOS
./mvnw test

# Windows
.\mvnw.cmd test
```

A suíte contém três níveis comprováveis no código:

- testes unitários de `AlunoService` com Mockito;
- testes de controller com `@WebMvcTest` e MockMvc;
- testes de persistência com `@DataJpaTest` e PostgreSQL 16 em Testcontainers.

O workflow de CI executa a mesma suíte no Ubuntu com Java 21. O Maven Wrapper mantém a versão do Maven reproduzível sem exigir instalação global.

## Estrutura principal

```text
src/
├── main/
│   ├── java/com/andre/escola_api/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/application.properties
└── test/java/com/andre/escola_api/
```

## Decisões técnicas

- JWT e `SessionCreationPolicy.STATELESS` evitam estado de sessão no servidor.
- DTOs evitam acoplar o contrato de criação de aluno diretamente à entidade persistida.
- Testcontainers exercita o mapeamento JPA contra PostgreSQL real e descartável.
- `spring.jpa.open-in-view=false` mantém o acesso ao banco fora da fase de serialização HTTP.

## Próximos passos

- Adotar migrations versionadas, como Flyway ou Liquibase, em vez de `ddl-auto=update`.
- Ampliar os testes de autenticação, autorização e tratamento de erros.
- Padronizar respostas de erro também para recursos inexistentes e conflitos de unicidade.
- Adicionar paginação e filtros à listagem de alunos.

## Autor

Desenvolvido por André Ferreira — [GitHub](https://github.com/AndreLopes30) · [LinkedIn](https://www.linkedin.com/in/andre-ferreira30)
