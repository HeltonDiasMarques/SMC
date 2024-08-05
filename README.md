# Sistema de Marcação de Consultas Médicas (SMC)
![SMC](https://github.com/user-attachments/assets/4ce19bc0-f00e-4daf-85b9-28abc427d0cb)

### 📝 Descrição
O Sistema de Marcação de Consultas Médicas (SMC) é um sistema backend desenvolvido em Java utilizando Spring Boot, com foco na gestão de agendamentos de consultas médicas. O sistema é projetado para gerenciar pacientes e médicos, oferecendo funcionalidades para agendar, cancelar e visualizar consultas médicas, além de gerenciar perfis de usuários e suas permissões.

### Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Security**: Implementado para segurança e autenticação.
- **Spring Boot Starter Data JDBC**: Para interação com o banco de dados PostgreSQL.
- **PostgreSQL**: Banco de dados relacional.
- **Lombok**: Biblioteca para redução de código boilerplate.
- **Swagger**: Documentação da API.
- **Postman**: Teste de APIs.
- **JDBC Template**: Camada de persistência de dados.
- **Enum**: Para tratamento de exceções, mensagens de erro e outras utilidades.

### Funcionalidades

#### Pacientes

- Cadastro e login;
- Atualização do cadastro;
- Login por meio do email e senha castrados do usuário;
- Consulta de todos os pacientes;
- Consulta de algum paciente por ID.

#### Médicos

- Cadastro e login;
- Atualização do cadastro;
- Login por meio do email e senha castrados do usuário;
- Consulta de todos os doutores;
- Consulta de algum doutor por ID.

#### Agendamento de Consultas

- Criação de consulta para o doutor.
- Marcação de consulta para o paciente.
- Cancelamento da consulta marcada pelo paciente.
- Busca de horários pelo ID do(a) Dr(a)..
- Busca de horários pelo ID do(a) paciente

📁 Estrutura do Projeto
A estrutura do projeto segue o padrão de camadas, conforme descrito abaixo:
```bash
SMC/ #Arquivos de configuração do projeto.
├── config/ #Configuração do projeto.
|   └── security/ #Configuração e outras utilidades referentes a segurança.
├── controller/ #Pontos de entrada da aplicação (endpoints).
├── dao/ #Camada de persistência de dados.
|    └── impl/
├── dto/ #Objetos de transferência de dados.
├── enums/ #Enumerações utilizadas no projeto.
├── exceptions/ #Exceções personalizadas.
├── model/ #Classes modelo(Patient, Doctor, Schedules, User e Address)
├── usecase/ #Camada de negócios, onde ficam as regras e lógica do projeto.
├── utils/ #Classes utilitárias e constantes.
└──SmcApplication/ #Classe principal para inicialização da aplicação.
```
##Passo a passo para utilização do projeto:
### 1. Configuração:

1.1 **Clone o repositório:**
    Primeiro vamos clonar do repositório remoto para um local em sua máquina.
    ```bash
    git clone https://github.com/seu-usuario/smc.git
    ```

2.1 **Configurar o banco de dados:**
    Após garantir que tudo foi clonado com sucesso, vamos criar o banco de dados e configurar o .yml
    - Crie um banco de dados PostgreSQL chamado de smc(Ou outro nome que preferir).
    - Atualize o arquivo `application.yml` com as credenciais do banco de dados criado por você.

3.1 **Executar a aplicação:**
    Então execute a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```
    Caso esta linha seja retornada, ela iniciou sem problemas: Started SmcApplication in 4.717 seconds (process running for 5.356)
### 2. Preparando o banco de dados.
    
### Documentação da API

A documentação da API está disponível no Swagger. Após iniciar a aplicação, acesse:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Testes

Para testar a aplicação, utilize o Postman para interagir com os endpoints expostos e verificar a funcionalidade do sistema.
