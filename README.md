# Sistema de Marcação de Consultas Médicas (SMC)
![SMC](https://github.com/user-attachments/assets/55b40093-7922-445e-bff3-c21477dd2d70)
## Descrição


O Sistema de Marcação de Consultas Médicas (SMC) é um sistema backend desenvolvido em Java utilizando Spring Boot, com foco na gestão de agendamentos de consultas médicas. O sistema é projetado para gerenciar pacientes, médicos e administradores, oferecendo funcionalidades para agendar, cancelar e visualizar consultas médicas, além de gerenciar perfis de usuários e suas permissões.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Security**: Implementado para segurança e autenticação (ou JWT, se a implementação de Spring Security for muito complicada).
- **Spring Boot Starter Data JDBC**: Para interação com o banco de dados PostgreSQL.
- **PostgreSQL**: Banco de dados relacional.
- **Lombok**: Biblioteca para redução de código boilerplate.
- **Swagger**: Documentação da API.
- **Postman**: Teste de APIs.
- **JDBC Template**: Camada de persistência de dados.
- **Enum**: Para tratamento de exceções e mensagens de erro.

## Funcionalidades

### Pacientes

- Cadastro e login;
- Atualização do cadastro;
- Login por meio do email e senha castrados do usuário;
- Consulta de todos os pacientes;
- Consulta de algum paciente por ID.

### Médicos

- Cadastro e login;
- Atualização do cadastro;
- Login por meio do email e senha castrados do usuário;
- Consulta de todos os doutores;
- Consulta de algum doutor por ID.

### Agendamento de Consultas

- Criação de consulta para o doutor.
- Marcação de consulta para o paciente.
- Cancelamento da consulta marcada pelo paciente.
- Busca de horários pelo ID do(a) Dr(a)..
- Busca de horários pelo ID do(a) paciente

## Estrutura do Projeto

- **Controller**: Ponto de entrada da aplicação (endpoints).
- **Dao**: Camada de persistência de dados.
- **UseCase**: Lógica de negócio.
- **DTO**: Objetos para mapeamento de entrada do usuário.
- **Exceptions**: Exceções personalizadas.
- **Config**: Configurações do projeto.
- **Utils**: Classes utilitárias e constantes.
- **Model**: Classes de domínio.

## Configuração

1. **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/smc.git
    ```

2. **Configurar o banco de dados:**
    - Crie um banco de dados PostgreSQL.
    - Atualize o arquivo `application.yml` com as credenciais do banco de dados.

3. **Executar a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

## Documentação da API

A documentação da API está disponível no Swagger. Após iniciar a aplicação, acesse:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Testes

Para testar a aplicação, utilize o Postman para interagir com os endpoints expostos e verificar a funcionalidade do sistema.
