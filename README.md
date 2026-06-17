# 🏫 Escola API

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Status](https://img.shields.io/badge/status-concluído-green?style=for-the-badge)

API REST de gerenciamento escolar desenvolvida em **Java com Spring Boot**, com autenticação JWT, persistência em PostgreSQL e arquitetura em camadas seguindo as boas práticas do mercado.

---

## 🎯 Objetivo

Este projeto foi construído para consolidar conceitos fundamentais do ecossistema Spring: mapeamento ORM com JPA/Hibernate, arquitetura em camadas, validação de dados, tratamento global de exceções e autenticação stateless com JWT.

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.5.14
- Spring Data JPA / Hibernate
- Spring Security
- PostgreSQL
- JWT (Auth0 java-jwt)
- Lombok
- Bean Validation (Jakarta Validation)
- Maven

---

## 📁 Estrutura do Projeto

```
escola-api/
├── src/main/java/com/andre/escola_api/
│   ├── config/
│   │   ├── SecurityConfigurations.java   # Configuração do Spring Security
│   │   └── SecurityFilter.java           # Filtro que intercepta e valida o JWT
│   ├── controller/
│   │   ├── AlunoController.java          # Endpoints CRUD de alunos
│   │   ├── AutenticacaoController.java   # Endpoint de login
│   │   └── UsuarioController.java        # Endpoint de cadastro de usuário
│   ├── dto/
│   │   ├── AlunoRequestDTO.java          # Dados de entrada para criar/editar aluno
│   │   ├── AlunoResponseDTO.java         # Dados de saída da API
│   │   ├── DadosAutenticacao.java        # Dados de login
│   │   └── DadosCadastroUsuario.java     # Dados de cadastro de usuário
│   ├── exception/
│   │   └── GlobalExceptionHandler.java   # Tratamento global de exceções
│   ├── model/
│   │   ├── Aluno.java                    # Entidade JPA de aluno
│   │   └── Usuario.java                  # Entidade JPA de usuário (UserDetails)
│   ├── repository/
│   │   ├── AlunoRepository.java
│   │   └── UsuarioRepository.java
│   └── service/
│       ├── AlunoService.java             # Regras de negócio de aluno
│       ├── AutenticacaoService.java       # UserDetailsService do Spring Security
│       └── TokenService.java             # Geração e validação de tokens JWT
└── src/main/resources/
    └── application.properties
```

---

## 🧠 Conceitos Aplicados

- **Arquitetura em camadas** — Controller → Service → Repository → Banco
- **ORM com JPA/Hibernate** — `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`
- **Injeção de dependência** — `@Autowired` via `@RequiredArgsConstructor` (Lombok)
- **DTOs** — separação entre o contrato da API e a entidade do banco
- **Bean Validation** — `@NotBlank`, `@NotNull`, `@Min`, `@Max`, `@Valid`
- **Tratamento global de exceções** — `@RestControllerAdvice` e `@ExceptionHandler`
- **Autenticação stateless com JWT** — geração, validação e filtro de segurança
- **Criptografia de senha** — BCrypt via `PasswordEncoder`
- **Spring Security** — `SecurityFilterChain`, `UserDetailsService`, `OncePerRequestFilter`

---

## 🔐 Autenticação

A API utiliza autenticação **stateless** baseada em JWT. O fluxo funciona assim:

1. O usuário se cadastra em `POST /usuarios`
2. O usuário faz login em `POST /login` e recebe um token JWT
3. O token deve ser enviado no header `Authorization: Bearer {token}` em todas as requisições às rotas protegidas
4. O `SecurityFilter` intercepta cada requisição, valida o token e autentica o usuário no contexto do Spring Security

---

## 🚀 Endpoints

### Autenticação

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/usuarios` | Cadastra um novo usuário | Não |
| `POST` | `/login` | Autentica e retorna o token JWT | Não |

### Alunos

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| `GET` | `/alunos` | Lista todos os alunos | Sim |
| `GET` | `/alunos/{id}` | Busca um aluno por ID | Sim |
| `POST` | `/alunos` | Cadastra um novo aluno | Sim |
| `PUT` | `/alunos/{id}` | Atualiza um aluno existente | Sim |
| `DELETE` | `/alunos/{id}` | Remove um aluno | Sim |

---

## 📦 Como rodar o projeto

### Pré-requisitos

- Java 17+
- PostgreSQL
- Maven (ou use o `mvnw` incluído no projeto)

### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/AndreLopes30/escola-api.git
cd escola-api
```

2. Crie um banco de dados PostgreSQL chamado `escola_api`

3. Configure o `src/main/resources/application.properties` com suas credenciais:
```properties
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

4. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 🧪 Testando com Postman

**1. Cadastrar usuário**
```http
POST /usuarios
Content-Type: application/json

{
  "login": "andre",
  "senha": "123456"
}
```

**2. Login**
```http
POST /login
Content-Type: application/json

{
  "login": "andre",
  "senha": "123456"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**3. Acessar rota protegida**
```http
GET /alunos
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 👨‍💻 Autor

Desenvolvido por **André Ferreira**
GitHub: [AndreLopes30](https://github.com/AndreLopes30)
LinkedIn: [andre-ferreira30](https://www.linkedin.com/in/andre-ferreira30)
