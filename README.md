BTG Challenge - Aplicação
📌 Descrição

Este projeto implementa um microserviço em Java (Spring Boot) que:

    Consome mensagens de pedidos via RabbitMQ.

    Persiste dados em MySQL.

    Expõe endpoints REST para consultar:

        Valor total de um pedido.

        Quantidade de pedidos por cliente.

        Lista de pedidos de um cliente.

    É totalmente dockerizado, com MySQL e RabbitMQ configurados via Docker Compose.

    Inclui testes unitários básicos.

📋 Pré-requisitos

Antes de rodar o projeto, instale:

    Docker e Docker Compose

        Download Docker Desktop (Windows/Mac)

        Linux: instalar pacotes docker e docker-compose-plugin.

    Java 17+ (se quiser rodar localmente sem Docker).

    Maven (se não usar o wrapper mvnw que já está no projeto).

🚀 Como rodar com Docker Compose

Clone o repositório:

    git clone https://github.com/seuusuario/btg-challenge.git
    cd btg-challenge

Suba a aplicação:

    docker compose up --build

Acesse:

    Aplicação: http://localhost:8080

    RabbitMQ Management: http://localhost:15673

        Usuário: guest

        Senha: guest

🛠 Testando o fluxo
Publicar mensagem no RabbitMQ

    Acesse o painel RabbitMQ: http://localhost:15673

Envie para a fila orders o seguinte JSON:

    {
      "codigoPedido": 1001,
      "codigoCliente": 1,
      "itens": [
        { "produto": "lapis", "quantidade": 100, "preco": 1.10 },
        { "produto": "caderno", "quantidade": 10, "preco": 5.00 }
      ]
    }

📡 Endpoints disponíveis

    GET /orders/{codigoPedido}/total → valor total do pedido.

    GET /customers/{codigoCliente}/orders/count → quantidade de pedidos por cliente.

    GET /customers/{codigoCliente}/orders → lista de pedidos do cliente.

Exemplo:

    curl http://localhost:8080/orders/1001/total

🧪 Rodando testes

./mvnw test

📂 Estrutura do projeto

├── src/main/java/...        # Código fonte
├── src/test/java/...        # Testes unitários
├── sql/init_db.sql          # Script SQL para criação automática do banco
├── docker-compose.yml       # Orquestração dos containers
├── Dockerfile               # Build da imagem da aplicação
├── pom.xml                  # Configuração Maven
└── README.md

🖥 Tecnologias utilizadas

    Java 17

    Spring Boot 3

    Spring Data JPA

    Spring AMQP

    MySQL 8

    RabbitMQ 3 Management

    Docker/Docker Compose

    Maven

    JUnit 5 / Mockito
