package br.com.fiap.techchallenge.lanchonete.bdd;

import br.com.fiap.techchallenge.lanchonete.*;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusPedidoEnum;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StepDefinition {

    private Response response;
    private ProdutoResponse produtoResponse;
    private ClienteResponse clienteResponse;
    private PedidoResponse pedidoResponse;
    private CobrancaResponse cobrancaResponse;

    @Quando("preencher todos os dados para cadastro do produto")
    public ProdutoResponse preencherTodosDadosParaCadastrarProduto() {
        var produtoRequest = ProdutoTestBase.criarProdutoRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoRequest)
                .when()
                .post("/produtos");
        return response.then()
                .extract()
                .as(ProdutoResponse.class);
    }

    @Então("o produto deve ser criado com sucesso")
    public void produtoDeveSerCriadoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deve exibir o produto cadastrado")
    public void deveExibirProdutoCadastrado() {
        var produtoRequest = ProdutoTestBase.criarProdutoRequest();
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"))
                .body("$", hasKey("id"))
                .body("nome", equalTo(produtoRequest.getNome()))
                .body("categoria", equalTo(produtoRequest.getCategoria().name()))
                .body("preco", equalTo(produtoRequest.getPreco().floatValue()))
                .body("descricao", equalTo(produtoRequest.getDescricao()));
    }

    @Dado("que um produto já está cadastrado")
    public void produtoJaCadastrado() {
        produtoResponse = preencherTodosDadosParaCadastrarProduto();
    }

    @Quando("realizar a busca do produto")
    public void realizarBuscaProduto() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/produtos/{id}", produtoResponse.getId());
    }

    @Então("o produto deve ser exibido com sucesso")
    public void produtoDeveSerExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Quando("realizar a requisição para alterar o produto")
    public void realizarRequisicaoParaAlterarProduto() {
        produtoResponse.setPreco(BigDecimal.valueOf(12.99));
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoResponse)
                .when()
                .put("/produtos/{id}", produtoResponse.getId());
    }

    @Então("o produto deve ser alterado com sucesso")
    public void produtoDeveSerAlteradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Então("deve exibir o produto alterado")
    public void deveExibirProdutoAlterado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"))
                .body("preco", equalTo(produtoResponse.getPreco().floatValue()));
    }

    @Quando("realizar a requisição para remover o produto")
    public void realizarRequisicaoParaRemoverProduto() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoResponse)
                .when()
                .delete("/produtos/{id}", produtoResponse.getId());
    }

    @Então("o produto deve ser removido com sucesso")
    public void produtoDeveSerRemovidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Quando("requisitar a lista de todos os produtos")
    public void requisitarListaProdutos() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/produtos");
    }

    @Então("os produtos devem ser exibidos com sucesso")
    public void produtosDevemSerExibidosComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("$", everyItem(anything()));
    }

    @Quando("realizar a busca do produto por categoria")
    public void realizarBuscaProdutoPorCategoria() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/produtos/categoria/{categoria}", produtoResponse.getCategoria().name());
    }

    @Quando("realizar a requisição para alterar a imagem do produto")
    public void realizarRequisicaoParaAlterarImagemProduto() {
        try {
            var imagem = new ClassPathResource("imagem_produto.jpg").getFile();
            response = given()
                    .multiPart("imagem", imagem)
                    .when()
                    .patch("/produtos/{id}", produtoResponse.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Então("a imagem do produto deve ser alterada com sucesso")
    public void imagemProdutoDeveSerAlteradaComSucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Quando("preencher todos os dados para cadastro do cliente")
    public ClienteResponse preencherTodosDadosParaCadastrarCliente() {
        var clienteRequest = ClienteTestBase.criarClienteRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when()
                .post("/clientes");
        return response.then()
                .extract()
                .as(ClienteResponse.class);
    }

    @Então("o cliente deve ser criado com sucesso")
    public void clienteDeveSerCriadoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deve exibir o cliente cadastrado")
    public void deveExibirClienteCadastrado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Dado("que um cliente já está cadastrado")
    public void clienteJaCadastrado() {
        clienteResponse = preencherTodosDadosParaCadastrarCliente();
    }

    @Quando("realizar a requisição para alterar o cliente")
    public void realizarRequisicaoParaAlterarCliente() {
        clienteResponse.setNome("Cliente Teste Alterado");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteResponse)
                .when()
                .put("/clientes/{id}", clienteResponse.getId());
    }

    @Então("o cliente deve ser alterado com sucesso")
    public void clienteDeveSerAlteradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Então("deve exibir o cliente alterado")
    public void deveExibirClienteAlterado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"))
                .body("nome", equalTo(clienteResponse.getNome()));
    }

    @Quando("requisitar a lista de todos os clientes")
    public void requisitarListaTodosClientes() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/clientes");
    }

    @Então("os clientes devem ser exibidos com sucesso")
    public void clientesDevemSerExibidosComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("$", everyItem(anything()));
    }

    @Quando("realizar a busca do cliente")
    public void realizarBuscaCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/clientes/{cpf}", clienteResponse.getCpf());
    }

    @Então("o cliente deve ser exibido com sucesso")
    public void clienteDeveSerExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ClienteResponseSchema.json"));
    }

    @Quando("preencher todos os dados para cadastro do pedido")
    public PedidoResponse preencherTodosDadosParaCadastrarPedido() {
        var pedidoRequest = PedidoTestBase.criarPedidoRequest(clienteResponse.getId(), produtoResponse.getId());
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequest)
                .when()
                .post("/pedidos");
        return response.then()
                .extract()
                .as(PedidoResponse.class);
    }

    @Então("o pedido deve ser criado com sucesso")
    public void pedidoDeveSerCriadoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deve exibir o pedido cadastrado")
    public void deveExibirPedidoCadastrado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
    }

    @Dado("que um pedido já está cadastrado")
    public void pedidoJaCadastrado() {
        pedidoResponse = preencherTodosDadosParaCadastrarPedido();
    }

    @Quando("realizar a busca do pedido por Id")
    public void realizarBuscaPedidoPorId() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/pedidos/{id}", pedidoResponse.getId());
    }

    @Então("o pedido deve ser exibido com sucesso")
    public void pedidoDeveSerExibidoComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
    }

    @Quando("realizar a busca do pedido por status")
    public void realizarBuscaPedidoPorStatus() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/pedidos/status/{status}", pedidoResponse.getStatus().name());
    }

    @Então("os pedidos devem ser exibidos com sucesso")
    public void pedidosDevemSerExibidosComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("$", everyItem(anything()));
    }

    @Quando("requisitar a lista de todos os pedidos")
    public void requisitarListaTodosPedidos() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/pedidos");
    }

    @Quando("realizar a requisição para alterar o pedido")
    public void realizarRequisicaoParaAlterarPedido() {
        pedidoResponse.setStatus(StatusPedidoEnum.EM_PREPARACAO);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoResponse)
                .when()
                .patch("/pedidos/{id}/status", pedidoResponse.getId());
    }

    @Então("o pedido deve ser alterado com sucesso")
    public void pedidoDeveSerAlteradoComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
    }

    @Dado("que um pedido já está no status em preparação")
    public void pedidoJaNoStatusEmPreparacao() {
        realizarRequisicaoParaAlterarPedido();
    }

    @Quando("realizar a busca do pedido na fila de preparação")
    public void realizarBuscaPedidoFilaPreparacao() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/pedidos/fila-producao");
    }

    @Quando("realizar a busca da cobrança por pedido")
    public void realizarBuscaCobrancaPorPedido() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/pedidos/{id}/cobranca", pedidoResponse.getId());
    }

    @Quando("preencher todos os dados para cadastro da cobrança")
    public CobrancaResponse preencherTodosDadosParaCadastrarCobranca() {
        var produtoRequest = CobrancaTestBase.criarCobrancaRequest(pedidoResponse.getId());
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoRequest)
                .when()
                .post("/cobrancas");
        return response.then()
                .extract()
                .as(CobrancaResponse.class);
    }

    @Então("a cobrança deve ser criada com sucesso")
    public void cobrancaDeveSerCriadaComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deve exibir a cobrança cadastrada")
    public void deveExibirCobrancaCadastrada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/CobrancaResponseSchema.json"));
    }

    @Dado("que uma cobrança já está cadastrada")
    public void cobrancaJaCadastrada() {
        cobrancaResponse = preencherTodosDadosParaCadastrarCobranca();
    }

    @Quando("realizar a busca da cobrança")
    public void realizarBuscaCobranca() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cobrancas/{id}", cobrancaResponse.getId());
    }

    @Então("a cobrança deve ser exibida com sucesso")
    public void cobrancaDeveSerExibidaComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/CobrancaResponseSchema.json"));
    }

    @Quando("realizar a requisição para alterar a cobrança")
    public void realizarRequisicaoParaAlterarCobranca() {
        var atualizaStatusCobrancaRequest = AtualizaStatusCobrancaTestBase
                .criarAtualizaStatusCobrancaRequest(StatusCobrancaEnum.PAGO);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(atualizaStatusCobrancaRequest)
                .when()
                .put("/cobrancas/{id}/status", cobrancaResponse.getId());
    }

    @Então("a cobrança deve ser alterada com sucesso")
    public void cobrancaDeveSerAlteradaComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Então("deve exibir a cobrança alterada")
    public void deveExibirCobrancaAlterada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/CobrancaResponseSchema.json"))
                .body("status", equalTo(StatusCobrancaEnum.PAGO.name()));
    }

    @Quando("realizar a requisição para alterar a cobrança no Mercado Pago")
    public void realizarRequisicaoParaAlterarCobrancaMercadoPago() {
        var webhookStatusCobrancaRequest = WebhookStatusCobrancaTestBase
                .criarWebhookStatusCobrancaRequest("approved", 1L);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(webhookStatusCobrancaRequest)
                .when()
                .put("/cobrancas/{id}/webhook-status", cobrancaResponse.getId());
    }

    @Então("a cobrança deve ser alterada com sucesso no Mercado Pago")
    public void cobrancaDeveSerAlteradaComSucessoMercadoPago() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Então("a cobrança deve ser exibida com resposta vazia")
    public void cobrancaDeveSerExibidaComRespostaVazia() {
        response.then()
                .body(is(emptyString()));
    }

}
