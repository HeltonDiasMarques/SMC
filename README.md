![SMC](https://github.com/user-attachments/assets/6a196111-80da-4acd-b688-71fa42181002)

O Sistema de Marcação de Consultas Médicas (SMC) é um sistema backend desenvolvido em Java utilizando Spring Boot, com foco na gestão de agendamentos de consultas médicas. O sistema é projetado para gerenciar pacientes e médicos, oferecendo funcionalidades para agendar, cancelar e visualizar consultas médicas, além de gerenciar perfis de usuários e suas permissões.

![DESCRIÇÃO (1)](https://github.com/user-attachments/assets/b870dfd5-7a9c-42af-8947-eab6b33fce59)

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

![DESCRIÇÃO (2)](https://github.com/user-attachments/assets/75cddd7b-711e-420b-b0ef-a8b1b45b14a1)

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
## Passo a passo para utilização do projeto:
### 0. Intalação:
**Instale essas ferramentas:**
0.1 Postgree: 
```bash
https://www.postgresql.org/download/
```
0.2 Intellij IDEA: 
```bash
https://www.jetbrains.com/idea/download/other.html
```
0.3 Postman: 
```bash
https://www.postman.com/downloads/
```
0.4 Git bash:
```bash
https://git-scm.com/downloads
```
### 1. Configuração:
1.1 **Abra o git bash**
2.1 **Clone o repositório:**
   - Primeiro vamos clonar do repositório remoto para um local em sua máquina.
   ```bash
    git clone https://github.com/seu-usuario/smc.git
   ```

3.1 **Configurar o banco de dados:**
    Após garantir que tudo foi clonado com sucesso, vamos criar o banco de dados e configurar o .yml
    - Crie um banco de dados PostgreSQL chamado de smc(Ou outro nome que preferir).
    - Atualize o arquivo `application.yml` com as credenciais do banco de dados criado por você.

4.1 **Executar a aplicação:**
   - Então execute a aplicação:
   ```bash
    ./mvnw spring-boot:run
   ```
   - Caso esta linha seja retornada, ela iniciou sem problemas: Started SmcApplication in 4.717 seconds (process running for 5.356)
     
### 2. Preparando o banco de dados.
2.1 **Clonar o repositorio que contem as tabelas e functions**
   Para preparar o banco de dados fiz um repositório que contém tudo que o sistema precisará:
   ```bash
      git clone https://github.com/HeltonDiasMarques/database_smc.git
   ```
Nele você terá acesso a todas as tabelas e functions  usadas para o funcionamento do projeto acontecer como o esperado.
2.2 **Utilizar
   
### Documentação da API

A documentação da API está disponível no Swagger. Após iniciar a aplicação, acesse:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Testes

Para testar a aplicação, utilize o Postman para interagir com os endpoints expostos e verificar a funcionalidade do sistema.
