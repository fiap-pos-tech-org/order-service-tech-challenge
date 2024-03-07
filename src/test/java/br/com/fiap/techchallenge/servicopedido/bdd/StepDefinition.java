package br.com.fiap.techchallenge.servicopedido.bdd;

import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import br.com.fiap.techchallenge.servicopedido.utils.ProdutoHelper;
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

    @Quando("preencher todos os dados para cadastro do produto")
    public ProdutoResponse preencherTodosDadosParaCadastrarProduto() {
        var produtoRequest = ProdutoHelper.criarProdutoRequest();
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
        var produtoRequest = ProdutoHelper.criarProdutoRequest();
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

    @Dado("que um cliente já está cadastrado")
    public void clienteJaCadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("http://localhost:8081/clientes/1");

        clienteResponse = response.then()
                .extract()
                .as(ClienteResponse.class);
    }

    @Quando("preencher todos os dados para cadastro do pedido")
    public PedidoResponse preencherTodosDadosParaCadastrarPedido() {
        var pedidoRequest = PedidoHelper.criarPedidoRequest(clienteResponse.getId(), produtoResponse.getId());
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

}
