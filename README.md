# Vertical-Logistica

### Documentação de Referência
Esta aplicação utiliza diversas bibliotecas e frameworks para implementar suas funcionalidades. Consulte a documentação abaixo para entender cada uma das dependências utilizadas:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.1/gradle-plugin/reference/htmlsingle/)
* [Spring Boot Starter Batch](https://docs.spring.io/spring-batch/reference/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.1/gradle-plugin/reference/htmlsingle/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.1/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [SpringDoc OpenAPI Starter WebMVC UI](https://springdoc.org/)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.6.1/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
* [Project Lombok](https://projectlombok.org/)
* [PostgreSQL](https://jdbc.postgresql.org/)


### Testes

* [Spring Boot Starter Test](https://docs.spring.io/spring-boot/reference/testing/index.html#testing)
* [Spring Batch Test](https://docs.spring.io/spring-batch/reference/)
* [JUnit Platform Launcher](https://junit.org/junit5/docs/current/user-guide/)

### Guides

* [Acessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest)
* [Documenting APIs with SpringDoc and OpenAPI](https://springdoc.org/)

### Instruções

### Pré-requisitos
- JDK 17
- Docker-compose
- [IntelliJ](https://www.jetbrains.com/idea/), Eclipse ou IDE com plugin para o Gradle

### Passos para Executar a Aplicação

* Importe o projeto na IDE como projeto gradle

```
    # Navegue até a raiz do projeto
    docker-compose up
    
    Ou
    
    Docker-compose up -d
```
* Acessar a Documentação Swagger:
- Depois que a aplicação estiver em execução, acesse a documentação do Swagger para testar a API.
- Abra o navegador:
```
    # Acesse a url:
    http://localhost:8080/swagger-ui.html

```

### Arquitetura

A aplicação foi desenvolvida utilizando o padrão Clean Architecture, criado por Robert C. Martin (Uncle Bob). A Clean Architecture organiza o código em camadas que se concentram na separação de responsabilidades e no isolamento da lógica de negócio das dependências externas, proporcionando uma aplicação flexível e de fácil manutenção.

* Princípios da Clean Architecture
* Independência da Camada de Domínio: O domínio da aplicação é independente de frameworks, facilitando a manutenção e a reutilização.
* Camadas bem Definidas: O código é dividido em camadas específicas para domínio, aplicação, adaptadores e infraestrutura.
* Ports e Adapters: A comunicação entre a camada de domínio e o mundo externo ocorre por meio de interfaces chamadas de Ports, e suas implementações são chamadas de Adapters.
* Facilidade de Testes: Como o domínio é isolado, é possível testar a lógica de negócios sem a necessidade de interagir diretamente com a infraestrutura.

# Estrutura de Camadas
* Core (Domínio): Contém as regras de negócio, classes de entidades e interfaces principais.
* Adapters: Implementa as interfaces do domínio para interagir com tecnologias externas, como controladores REST, repositórios e mapeamento de dados.
* Application: Orquestra a lógica de negócios, conectando o domínio com os adaptadores.
* Infraestrutura: Contém detalhes específicos de implementação, como configuração de banco de dados e integração.



### Princípios SOLID
A aplicação também adota os princípios SOLID, que ajudam a manter o código organizado, flexível e de fácil manutenção:

- Single Responsibility Principle (SRP): Cada classe tem uma única responsabilidade, o que torna o código mais modular e reduz a complexidade.
- Open/Closed Principle (OCP): As classes são abertas para extensão, mas fechadas para modificação, permitindo adicionar novas funcionalidades sem alterar o código existente.
- Liskov Substitution Principle (LSP): Subtipos podem substituir seus tipos base sem alterar a funcionalidade do programa.
- Interface Segregation Principle (ISP): Interfaces são específicas e focadas em uma responsabilidade, evitando classes que implementam métodos que não utilizam.
- Dependency Inversion Principle (DIP): O código depende de abstrações e não de implementações concretas, facilitando a testabilidade e a troca de dependências.

