## Visão Geral

API REST em Java/Spring Boot para gerenciamento de usuários, endereços e telefones, com autenticação JWT.

## Tecnologias e Pré-requisitos

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **Spring Security**
* **JWT (JJWT)**
* **Banco de dados:** PostgreSQL
* **Build tool:** Gradle

## Configuração do Projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/NclFrei/UsuarioAPI.git
   cd usuario-api
   ```

2. **Configuração do Banco de Dados (local)**
   Edite o arquivo `src/main/resources/application.properties` e configure sua instância do PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_usuario   # URL do banco
spring.datasource.username=postgres                                 # Usuário do banco
spring.datasource.password=Senha                                    # Senha do banco
```

> **Observação:** As demais propriedades do JPA (como `hibernate.ddl-auto`, `show-sql` e `database-platform`) podem permanecer conforme padrão do projeto.

> **Observação:** O JWT é gerado automaticamente pela classe `JwtUtil`, portanto não é necessário definir um `jwt.secret` manualmente.

## Como Executar

* **Com Gradle:**

  ```bash
  ./gradlew bootRun
  ```

A aplicação iniciará na porta **8080** por padrão.

## Endpoints

### 1. Cadastro de Usuário

* **POST** `/usuario`
* **Request Body:**

  ```json
  {
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "senha123",
    "enderecos": [
      {
        "rua": "Rua A",
        "numero": 100,
        "complemento": "Apt 101",
        "cidade": "São Paulo",
        "estado": "SP",
        "cep": "01001-000"
      }
    ],
    "telefones": [
      { "ddd": "11", "numero": "999999999" }
    ]
  }
  ```
* **Response 200 OK:** Retorna o `UsuarioDTO` criado.

### 2. Login

* **POST** `/usuario/login`
* **Request Body:**

  ```json
  { "email": "joao@example.com", "senha": "senha123" }
  ```
* **Response 200 OK:**

  ```text
  Bearer <token_jwt>
  ```

### 3. Buscar Usuário por Email

* **GET** `/usuario?email={email}`
* **Response 200 OK:** Retorna o `UsuarioDTO` correspondente.

### 4. Deletar Usuário por Email

* **DELETE** `/usuario/{email}`
* **Response 200 OK:** Sem conteúdo.

### 5. Atualizar Dados do Usuário

* **PUT** `/usuario`
* **Headers:** `Authorization: Bearer <token>`
* **Request Body:** Campos opcionais do `UsuarioDTO` (ex.: nome, senha).
* **Response 200 OK:** Retorna o `UsuarioDTO` atualizado.

### 6. Cadastrar Endereço

* **POST** `/usuario/endereco`
* **Headers:** `Authorization: Bearer <token>`
* **Request Body:**

  ```json
  {
    "rua": "Rua B",
    "numero": 200,
    "complemento": "Casa",
    "cidade": "Rio de Janeiro",
    "estado": "RJ",
    "cep": "20000-000"
  }
  ```
* **Response 200 OK:** Retorna o `EnderecoDTO` criado.

### 7. Atualizar Endereço

* **PUT** `/usuario/endereco`
* **Headers:** `id: <idEndereco>`
* **Request Body:** Campos do `EnderecoDTO` a alterar.
* **Response 200 OK:** Retorna o `EnderecoDTO` atualizado.

### 8. Cadastrar Telefone

* **POST** `/usuario/telefone`
* **Headers:** `Authorization: Bearer <token>`
* **Request Body:**

  ```json
  { "ddd": "21", "numero": "888888888" }
  ```
* **Response 200 OK:** Retorna o `TelefoneDTO` criado.

### 9. Atualizar Telefone

* **PUT** `/usuario/telefone`
* **Headers:** `id: <idTelefone>`
* **Request Body:** Campos do `TelefoneDTO` a alterar.
* **Response 200 OK:** Retorna o `TelefoneDTO` atualizado.

## Tratamento de Erros

* **409 Conflict:** Email já cadastrado.
* **404 Not Found:** Recurso (usuário, endereço ou telefone) não encontrado.

