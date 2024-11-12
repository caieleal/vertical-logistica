# vertical-logistica

Esta aplicação é uma API REST para gerenciamento de operações logísticas. Utiliza uma arquitetura modular e aplica os princípios SOLID para manter o código simples, legível e escalável.

Arquitetura e Design
Tecnologias Utilizadas
Java 17: Linguagem principal para a implementação da API.
Spring Framework: Para configuração da aplicação, gerenciamento de dependências, controle de injeção e simplificação do desenvolvimento com Spring Boot.
PostgreSQL: Banco de dados relacional utilizado para persistência dos dados.
Swagger com Springdoc OpenAPI: Para documentação interativa da API, facilitando a visualização e teste dos endpoints.
Decisões Arquiteturais
O projeto segue uma arquitetura hexagonal com foco na separação de responsabilidades e aplicação dos princípios SOLID. Com essa abordagem, mantemos um código modular, onde a lógica de negócio é independente de detalhes de infraestrutura, como persistência de dados e protocolos de comunicação.

Estrutura de Pacotes
Abaixo estão as descrições de cada pacote e sua função na aplicação:

adapters: Camada responsável por adaptar a aplicação para ambientes externos.

controller: Contém os controllers da API, responsáveis por receber as requisições HTTP e retornar respostas. Segue o princípio da responsabilidade única, onde cada controller é responsável por um conjunto específico de endpoints.
dto: Define os Data Transfer Objects, que são usados para transferir dados entre camadas da aplicação.
response: Contém as classes de resposta para os endpoints da API, garantindo que a interface de saída esteja desacoplada da lógica interna.
repository: Define interfaces para o acesso a dados, seguindo o padrão de repositório para desacoplar a camada de persistência.
domain: Representa a camada de domínio, onde estão os elementos essenciais da lógica de negócios.

entities: Contém as entidades de domínio que representam as tabelas do banco de dados e os dados de negócio da aplicação.
model: Inclui objetos de valor ou outros modelos que representam abstrações da lógica de negócios.
factory: Padrão de fábrica para criação de objetos complexos, centralizando a construção de instâncias e mantendo o código organizado.

mapper: Utiliza o padrão de mapeamento para converter objetos de domínio em DTOs e vice-versa, promovendo a separação entre camadas.

service: Contém as implementações dos serviços principais de negócio, aplicando regras e lógica específica.

usecase: Camada responsável pelos casos de uso. Define a lógica de negócio específica para cada cenário, desacoplada de infraestrutura e interfaces externas.

utils: Contém utilitários e classes de apoio, como validadores e métodos auxiliares.

Princípios SOLID
O projeto adota os princípios SOLID para garantir modularidade e manutenção fácil:

Responsabilidade Única (SRP): Cada classe tem uma responsabilidade única, como mapeamento (Mapper), controle (Controller) e serviços de negócios (Service).
Aberto-Fechado (OCP): A estrutura de pacotes permite fácil extensão sem modificação nas classes existentes.
Substituição de Liskov (LSP): Todas as interfaces e abstrações são implementadas de forma que podem ser substituídas por subclasses sem comprometer a funcionalidade.
Segregação de Interface (ISP): Interfaces são definidas de forma granular, especialmente na camada de repositório.
Inversão de Dependência (DIP): As dependências de infraestrutura (por exemplo, banco de dados) são injetadas nas classes de serviço e repositórios, usando o Spring para injeção de dependência.
Execução da Aplicação
A aplicação está pronta para execução via Docker e utiliza dois contêineres Docker para a API e o banco de dados PostgreSQL.

Pré-requisitos
Docker: Certifique-se de que o Docker está instalado para execução dos contêineres.
Arquivos Docker
Dockerfile: Configura o ambiente e constrói a imagem da aplicação.
docker-compose.yml: Configura o banco de dados e a aplicação para rodarem em contêineres separados, conectados pela mesma rede Docker.
Instruções para Execução
Construir e Executar a Aplicação

A partir da raiz do projeto, execute os seguintes comandos para iniciar o ambiente:

bash
Copiar código
docker-compose up --build
Esse comando irá:

Construir a imagem da aplicação Spring Boot.
Iniciar um contêiner PostgreSQL.
Conectar os contêineres na mesma rede para que a aplicação tenha acesso ao banco de dados.
Acessar a API

A aplicação estará disponível em http://localhost:8080.
Acesse a documentação Swagger para visualizar e testar os endpoints:
bash
Copiar código
http://localhost:8080/swagger-ui.html
Endpoints
A API expõe diversos endpoints para gerenciamento de dados logísticos. Exemplos:

GET /filter/{startDate}/{endDate}: Filtra ordens por intervalo de datas.
POST /upload: Endpoint para upload de arquivos.
Cada endpoint está documentado no Swagger, com exemplos e descrições detalhadas.

Banco de Dados
A aplicação utiliza o PostgreSQL para armazenamento dos dados. O Docker Compose está configurado para criar um banco de dados chamado verticallogisticadb e usa as credenciais padrão definidas no docker-compose.yml.

Documentação e Testes
Swagger: Toda a documentação da API está disponível pelo Swagger.
Testes: Os testes foram desenvolvidos para garantir que cada funcionalidade chave esteja funcionando corretamente, com cobertura para serviços e repositórios.

