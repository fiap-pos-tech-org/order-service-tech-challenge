# Sistema de Controle de Pedidos para Lanchonete

Este projeto é um sistema de controle de pedidos para uma lanchonete. Ele possui as seguintes funcionalidades:

- Cadastro e gerenciamento de clientes
- Cadastro e gerenciamento de produtos
- Criação, atualização e acompanhamento de pedidos

## Rotas disponíveis na API
Todas as rotas estão listadas através do Swagger no endereço `http://localhost:8080/swagger-ui/index.html`

Obs.: Sugerimos o Insomnia e já disponibilizamos uma collection em `collections/insomnia` 

## Pré-requisitos

Antes de executar o projeto, verifique se você possui os seguintes requisitos:

- Docker e docker-compose instalados
- Java 17 (caso queira buildar o projeto fora do container)

## Imagem Docker do projeto

Podemos encontrar a imagem do projeto no seguinte repositório do Docker hub [geter/pedido-techchallenge38](https://hub.docker.com/repository/docker/geter/techchallenge38/general)

## Executando o Projeto

Siga as instruções abaixo para executar o projeto via docker/docker-compose:

1. Faça o clone deste repositório: `https://github.com/fiap-pos-tech-org/order-service-tech-challenge.git`
2. Acesse o diretório do projeto: `cd order-service-tech-challenge`
3. Execute o comando para iniciar o ambiente Docker: `docker-compose up -d`
4. Aguarde até que os containers estejam prontos e em execução.
5. Acesse a API pelo seu client de escolha pelo seguinte endereço base: `http://localhost:8080`

### Caso queira buildar o projeto fora do container, siga os passos abaixo:

1. Certifique-se de ter o Java 17 instalado em sua máquina.
2. Acesse o diretório do projeto: 
    ```bash 
        cd order-service-tech-challenge
    ```
3. Execute o comando para buildar o projeto: 
    ```bash
        ./mvnw clean package
    ```
4. Execute o comando para iniciar o ambiente Docker: 
    ```bash
        docker-compose -f docker-compose-local.yml up -d
    ```
5. Execute o comando para executar a aplicação: 
    ```bash
        ./mvnw spring-boot:run
    ```
6. Acesse a API pelo seu client de escolha pelo seguinte endereço base: `http://localhost:8080`

### Caso queira rodar o projeto dentro de um cluster kubernetes local

1. Certifiquece de ter o kubectl instalado e devidamente configurado para cluster kubernetes
2. Execute o comando: 
    ```bash
        kubectl apply -f deployment
    ```
3. Aguarde até que os pods estejam com status de RUNNING. **Os pods da applicação podem apresentar erros até que o mysql termine de subir**
4. Acessando o serviço: 
    - Acesse a API pelo seu client de escolha pelo seguinte endereço base: `http://{IP_DO_SEU_CLUSTER}:30000`
    - Se você estiver utilizando o minikube obetenha a url da aplição através do comando:
    ```bash 
        minikube service pedido-service --url
    ```

### Para rodar os testes do projeto execute os comandos abaixo:

1. Testes unitários:
    ```bash
        mvn test
    ```
2. Testes integrados:
    ```bash
        mvn test -P integration-test
    ```
3. Testes de sistema:
    ```bash
        mvn test -P system-test
    ```
4. Testes de sistema filtrando por tags:
    ```bash
        mvn test -P system-test -Dcucumber.filter.tags="@smoke"
    ```

### Para verificar a cobertura dos testes no ambiente local execute os comandos abaixo:

1. Jacoco:
    ```bash
        mvn test
        mvn jacoco:report
    ```

2. Sonar local:
    ```bash
        docker compose -f docker-compose-local.yml up -d
        mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=sonar
    ```

## Padrão Saga Coreografada
O padrão Saga Coreografada foi adotado não apenas no microserviço de pagamento de pedidos da lanchonete, mas também em outros serviços como pedido e produção. Essa escolha foi feita para garantir uma abordagem consistente e coerente na coordenação de transações distribuídas em todo o sistema da lanchonete, garantindo a consistência e a integridade dos dados em um ambiente distribuído.

Além disso, a utilização do padrão Saga Coreografada entre os diferentes microserviços permite uma comunicação eficiente e assíncrona entre eles, reduzindo a complexidade e melhorando a escalabilidade do sistema como um todo. Isso facilita a manutenção e evolução contínua do sistema, uma vez que cada microserviço pode ser desenvolvido, testado e implantado de forma independente.

Portanto, ao adotar o padrão Saga Coreografada em todos os microserviços da lanchonete, temos uma arquitetura resiliente e flexível, capaz de lidar com transações distribuídas de maneira eficiente e confiável em todo o ecossistema da lanchonete.

## Desenho de arquitetura utilizando o Padrão Saga Coreografada

![lanchonete_diagrama_saga_coreografada_v2.png](lanchonete_diagrama_saga_coreografada_v2.png)

## Contribuidores
- [pedroprj](https://github.com/pedroprj) - Pedro Pereira dos Reis Júnior - pedrojr9119@gmail.com - RM 350295
- [diego-jo](https://github.com/diego-jo) - Diego José Oliveira - oliveiraj.diego@gmail.com - RM 350296
