# OrdemMS (Order Management System)

Este projeto é uma solução desenvolvida como parte de um desafio técnico para a vaga de Engenheiro de Software no **BTG Pactual**. O sistema de gerenciamento de pedidos oferece uma API RESTful para gerenciar clientes e pedidos, implementada com o objetivo de demonstrar habilidades em desenvolvimento de software, integração com bancos de dados NoSQL, mensageria com RabbitMQ e contêinerização usando Docker.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal do projeto.
- **Spring Boot**: Framework utilizado para criar a API RESTful de forma rápida e eficiente.
- **MongoDB**: Banco de dados NoSQL utilizado para armazenar os dados de clientes e pedidos.
- **MongoRepository**: Interface do Spring Data utilizada para integrar a aplicação com o MongoDB, facilitando operações CRUD.
- **RabbitMQ**: Sistema de mensageria utilizado para comunicação assíncrona entre microservices e outras partes do sistema. RabbitMQ é utilizado para enviar e receber mensagens relacionadas a pedidos e outras operações em background.
- **Docker**: Utilizado para criar contêineres para o ambiente de desenvolvimento e produção, facilitando a portabilidade e a escalabilidade da aplicação.
- **Maven**: Gerenciador de dependências e build.
- **Postman / Insomnia**: Ferramentas utilizadas para testar e validar a API de forma eficiente durante o desenvolvimento.

## Como Rodar o Projeto

### Requisitos

- **JDK 21**: Necessário para rodar o projeto.
- **MongoDB**: Você precisa ter o MongoDB instalado e em funcionamento localmente ou utilizar uma instância na nuvem.
- **RabbitMQ**: Você precisa ter o RabbitMQ instalado localmente ou utilizar uma instância na nuvem.
- **Docker**: Você precisa ter o Docker, para rodar os serviços de forma isolada e consistente.

### Passos para rodar o projeto localmente

1. **Clone o repositório**:

    ```bash
    git clone https://github.com/BrunG9/orderms.git
    cd orderms
    ```

2. **Instale o MongoDBCompass**:

    Caso ainda não tenha o MongoDB instalado localmente, você pode baixá-lo [aqui](https://www.mongodb.com/try/download/community). Após a instalação, certifique-se de que o MongoDB está rodando na porta padrão (27017).

3. **Instale o RabbitMQ**:

    Caso ainda não tenha o RabbitMQ instalado localmente, você pode seguir o guia de instalação [aqui](https://www.rabbitmq.com/download.html). O RabbitMQ deve estar em funcionamento, geralmente na porta padrão `5672`.

4. **Configuração do MongoDB e RabbitMQ (caso necessário)**:

    Se necessário, configure as credenciais do MongoDB e RabbitMQ nos arquivos `application.properties` ou `application.yml` dentro da pasta `src/main/resources`, para que a aplicação consiga se conectar corretamente a ambos os serviços.

    Exemplo de configuração no arquivo `application.properties`:

    ```properties
    # MongoDB
    spring.data.mongodb.uri=mongodb://localhost:27017/orderms

    # RabbitMQ
    spring.rabbitmq.host=localhost
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest
    ```

5. **Compile e execute o projeto**:

    Caso tenha o Maven instalado, você pode compilar e executar o projeto com o seguinte comando:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

6. **Acesse a API localmente**:

    Após rodar o comando acima, o servidor estará disponível localmente em `http://localhost:8080`.

---

## Usando Docker para Rodar o Projeto

O projeto pode ser facilmente contêinerizado usando Docker para facilitar a execução dos serviços como o MongoDB, RabbitMQ e a aplicação em si.

### Passos para rodar o projeto com Docker

1. **Crie a imagem Docker para o projeto**:

    No diretório raiz do projeto, execute o seguinte comando para criar a imagem Docker:

    ```bash
    docker build -t orderms .
    ```

2. **Execute os serviços com Docker Compose**:

    O projeto inclui um arquivo `docker-compose.yml` para facilitar a execução de múltiplos contêineres. Ele já inclui serviços para o MongoDB, RabbitMQ e a aplicação. Para rodar o projeto com Docker Compose, basta executar:

    ```bash
    docker-compose up
    ```

    Isso iniciará os contêineres para o MongoDB, RabbitMQ e a aplicação `orderms`.

3. **Acesse a API localmente**:

    Após os contêineres estarem em execução, a API estará disponível em `http://localhost:8080`.

---

## Como Testar a Requisição `localhost:8080/customers/1/orders`

Você pode testar a requisição de obter os pedidos de um cliente usando o Postman ou o Insomnia.

### Usando o Postman ou Insomnia

1. Abra o Postman ou o Insomnia.
2. Crie uma nova requisição do tipo **GET**.
3. No campo de URL, insira o seguinte endereço:

    ```
    http://localhost:8080/customers/1/orders
    ```

4. Clique em **Send** para enviar a requisição.
5. Você deverá receber a resposta da API com os pedidos associados ao cliente com `id = 1`.

### Exemplo da mensagem que deve ser consumida no RabbitMq:

```json
{
       "codigoPedido": 1001,
       "codigoCliente":1,
       "itens": [
           {
               "produto": "lápis",
               "quantidade": 100,
               "preco": 1.10
           },
           {
               "produto": "caderno",
               "quantidade": 10,
               "preco": 1.00
           }
       ]
   }
```

### Exemplo de Resposta

A resposta da API para esta requisição pode ser semelhante ao seguinte formato JSON (dependendo dos dados presentes no MongoDB):

```json
{
	"summary": {
		"totalOnOrders": 9000.00
	},
	"data": [
		{
			"orderId": 1001,
			"customerId": 1,
			"total": 4500.00
		},
		{
			"orderId": 1002,
			"customerId": 1,
			"total": 4500.00
		}
	],
	"paginationResponse": {
		"page": 0,
		"pageSize": 10,
		"totalElements": 2,
		"totalPages": 1
	}
}
```
