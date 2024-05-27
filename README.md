# Cadastro de Beneficiários de Plano de Saúde

Esta é uma simples aplicação Spring Boot que demonstra o consumo de API para cadastro de beneficiário e seus documentos através de endereços REST com autenticação de usuário.

## Funcionalidades

- Registro de usuário: Permite que os usuários registrem com nome de usuário e senha.
- Autenticação de usuário: Valida as credenciais do usuário e gera um token JWT para as requisições da API subsequentes.
- Atenticação baseada em JWT: Segura que os endereços de API sejam impedidas por JWT tokens.
- Retorno de detalhes do usuário: Permite que seja acessado os detalhes do usuário com JWT Token.
- Ajuste de autenticação dinâmica: Habilita a mudança de autoridade de nível em tempo de execução com Spring Boot Actuator.
- Cadastro de novos beneficiários junto com os objetos de documento
- Atualização inteira do beneficiário através do verbo PUT
- Deletar um beneficiário
- Listar todos os beneficiários cadastrados
- Listar todos os documentos cadastrados para um beneficiário
- Swagger com anotações importantes sobre cada endereço da API REST

## Tecnologias Usadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2 Database
- JWT (JSON Web Tokens)
- Spring Boot Actuator
- Swagger
- JUnit

## Prerequisitos

- Java 17 ou mais recente instalado em seu computador.
- Maven instalado em seu computador.

## Começando

Clone este repositório em seu computador:

```
git clone https://github.com/pqnoje/ekan-teste.git
```

Navegue até o diretório do projeto:

```
cd ekan-teste
```

Construa o projeto com Maven:

```
mvn clean package
```

Rode a aplicação:

  - **Usando o Maven** <br/>``` mvn spring-boot:run```
  - **Pelo arquivo jar**
    Crie o arquivo jar usando o comando ```mvn clean install``` depois execute
    <br/>```java -jar target/<jar_filename>.jar```
  - **Direto pela IDE**
    <br/>```Botão direito em  Application.java e clique em 'Run'```
    <br/><br/>

Assim que a aplicação rodar você pode acessar os endereços da API através de chamadas HTTP pelo endereço URL: http://localhost:8080.

## Endereços de API

### Registro de usuários

- **URL:** `/api/register`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "username": "exemplo_usuario",
    "password": "exemplo_senha"
  }
  ```
- **Resposta:** `"Registration successful"`

### Autenticação de usuário

- **URL:** `/api/authenticate`
- **Method:** `POST`
- **Request Body:**
```json
{
    "username": "exemplo_usuario",
    "password": "exemplo_senha"
}
```
- **Resposta:** JWT token

### Retornar os detalhes do usuário

- **URL:** `/api/user`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Resposta:** Um objeto JSON com os detalhes do usuário

### Cadastra um novo beneficiário

- **URL:** `/api/beneficiary`
- **Method:** `POST`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
{
    "name": "Jefferson Fernandes de Lucena",
    "phone": "11950761002",
    "birthdate": "1990-06-22",
    "documents":  [
        {
            "type": "RG",
            "description": "92347201X"
        },
        {
            "type": "CPF",
            "description": "93892801923"
        }
    ]
}
```
- **Resposta:** Um objeto JSON com os detalhes do beneficiário

### Atualiza um beneficiário

- **URL:** `/api/beneficiary/id`
- **Method:** `PUT`
- **Headers:** `Authorization: Bearer <JWT Token>`
- **Request Body:**
  ```json
{
    "name": "Jefferson Fernandes de Lucena",
    "phone": "11950762020",
    "birthdate": "1990-06-22"
}
```
- **Resposta:** Um objeto JSON com os detalhes do beneficiário

### Deleta um beneficiário

- **URL:** `/api/beneficiary/id`
- **Method:** `DELETE`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Uma mensagem dizendo que o beneficiário foi deletado

### Lista de todos os beneficiários

- **URL:** `/api/beneficiary`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Uma lista de todos os beneficiários cadastrados

### Lista de todos os documentos de um beneficiário

- **URL:** `/api/beneficiary/id/document`
- **Method:** `GET`
- **Headers:** `Authorization: Bearer <JWT Token>`

- **Resposta:** Uma lista de todos os documentos cadastrados para o beneficiário informado

## Swagger

- Para acessar o Swagger acesse a URI: /swagger-ui/index.html

## Testes unitários

- Para rodar os testes unitários rode o comando `mvn install`.

## Configuração

- A chave secreta de JWT e a expiração do token podem ser configuradas no arquivo `application.properties`.
- O banco de dados em memória H2 é usado por propósito de demonstração. Você pode alterar o banco de dados ao configurar em `application.properties`.

## Segurança

- As senhas são criptografadas usando algorítimos BCrypt para armazenar no banco de dados.
- Os endereços HTTP são seguros por Spring Security e JWT Tokens.
- Os tokens JWT são validados antes de garantir acesso aos endereços HTTP protegidos.
