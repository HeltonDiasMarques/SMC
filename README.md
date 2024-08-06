![codigosql](https://github.com/user-attachments/assets/0a9c7ea2-e6e7-406a-9663-1f5fdaa0cfd3)![SMC](https://github.com/user-attachments/assets/6a196111-80da-4acd-b688-71fa42181002)

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

![DESCRIÇÃO (3)](https://github.com/user-attachments/assets/5d0e0fe9-2c82-4dd1-a895-033d447e176d)
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

![DESCRIÇÃO (4)](https://github.com/user-attachments/assets/c91ed434-8205-4910-ac18-95b7767a4ee8)
### 0. Intalação:
**Instale essas ferramentas:**

0.1 Postgree: https://www.postgresql.org/download/

0.2 Intellij IDEA: https://www.jetbrains.com/idea/download/other.html

0.3 Postman: https://www.postman.com/downloads/

0.4 Git bash: https://git-scm.com/downloads

### 1. Configuração:
1.1 Crie uma pasta onde você irá guardar o repósitorio.
1.2 **Abra o git bash**
Após instalar o Git bash em sua máquina, para abri-lo, clicar com o botão direito na pasta que criamos anteriormente, então clicar com o botão esquerdo na opçao Open Git Bash here.
![bash](https://github.com/user-attachments/assets/fb02f82b-ad07-4f7a-b6b6-27b2cf2aa609)

Então esse terminal irá abrir:

![Terminal bash](https://github.com/user-attachments/assets/57e52c6d-75f0-46c0-a679-cc1fff958074)

Verifique se o caminho está realmente correto.

2.1 **Clone o repositório:**

Após criarmos a pasta e termos aberto o Git bash, vamos executar o seguinte comando no terminal:
```bash
git clone https://github.com/HeltonDiasMarques/SMC.git
```
![git clone](https://github.com/user-attachments/assets/3829ae51-e5e5-4dbd-bd44-6014ec94a4e3)

Caso dê tudo certo, isso será apresentado.
3.1 **Configurar o banco de dados:**
Agora que terminamos de preparar o repósirotio local, vamos configurar o banco de dados(Database).
- Vamos começar criando um banco de dados, para isso clique com botão direito em "Databases" cloque o mouse encima de "Create" e clique com o botão esquerdo em "Database..."
  
![criandoBanco](https://github.com/user-attachments/assets/1bc1bfd5-10fe-4097-85dc-71c25e7bf54a)

Então uma aba irá se abrir, lá será possivel definir o nome do seu banco de dados, recomendo que utilize um nome que remeta ao projeto, no meu caso será "smc":

![nomeando o bancp](https://github.com/user-attachments/assets/8875d758-1fed-444b-bda7-9896d9cc6d70)

Após acabar, clique em "Save".

- Com isso, estaremos prontos para inserir as functions e tabelas no banco de dados(Database), criei um repósitorio que contém todas as tabelas e as functions necessarias para o funcionemento do projeto.
Link do repóstorio: https://github.com/HeltonDiasMarques/database_smc

Caso queira apenas clonar, faça o mesmo processo de clonagem anterior, mas agora utilizando
```bash
git clone https://github.com/HeltonDiasMarques/database_smc.git
```
- Você terá acesso a duas pastas, uma chamada "Functions" e outra chamada "Tables"
Vou exemplificar com uma function, mas será o mesmo processo, tanto para as outras functions, quanto para as tabelas.
Vamos começar com você abrindo a pasta functions, então clicando com o botão direito em uma das function, coloque o mouse encima da opção "Abrir com >" e escolha a opção "Bloco de Notas".

![Abrindo o sql](https://github.com/user-attachments/assets/e620eebb-131f-45dc-bd04-1682fd58acbe)

- Então será aberto o bloco de notas com este Script:

![codigosql](https://github.com/user-attachments/assets/58ebcf15-0bf7-470b-ba52-376c20fc8081)

Copie todo o código e abra o pgAdmin4 novamente.


2.2 **Utilizar
   
### Documentação da API

A documentação da API está disponível no Swagger. Após iniciar a aplicação, acesse:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Testes

Para testar a aplicação, utilize o Postman para interagir com os endpoints expostos e verificar a funcionalidade do sistema.
