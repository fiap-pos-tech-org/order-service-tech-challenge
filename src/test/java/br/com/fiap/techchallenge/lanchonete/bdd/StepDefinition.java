package br.com.fiap.techchallenge.lanchonete.bdd;

import br.com.fiap.techchallenge.lanchonete.ProdutoTestBase;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.ProdutoRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.ProdutoResponse;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class StepDefinition {

    private static final String ENDPOINT_PRODUTOS = "http://localhost:8080/produtos";

    private Response response;
    private ProdutoResponse produtoResponse;

    @Quando("preencher todos os dados para cadastro do produto")
    public void preencherTodosDadosParaCadastrarProduto() {
        var produtoRequest = ProdutoTestBase.criarProdutoRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoRequest)
                .when()
                .post(ENDPOINT_PRODUTOS);
    }

    @Então("o produto deve ser criado com sucesso")
    public void o_produto_deve_ser_criado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deve exibir o produto cadastrado")
    public void deveExibirProdutoCadastrado() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/ProdutoResponseSchema.json"));
    }

    @Dado("que um produto já está cadastrado")
    public void produtoJaCadastrado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("realizar a busca do produto")
    public void realizarBuscaProduto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("o produto deve ser exibido com sucesso")
    public void produtoDeveSerExibidoComSucesso() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("realizar a requisição para alterar o produto")
    public void realizarRequisicaoParaAlterarProduto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("o produto deve ser alterado com sucesso")
    public void produtoDeveSerAlteradoComSucesso() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("deve exibir o produto alterado")
    public void deveExibirProdutoAlterado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("realizar a requisição para remover o produto")
    public void realizarRequisicaoParaRemoverProduto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("o produto deve ser removido com sucesso")
    public void produtoDeveSerRemovidoComSucesso() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("requisitar a lista de todos os produtos")
    public void requisitarListaProdutos() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("os produtos devem ser exibidos com sucesso")
    public void produtosDevemSerExibidosComSucesso() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("realizar a busca do produto por categoria")
    public void realizarBuscaProdutoPorCategoria() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("realizar a requisição para alterar a imagem do produto")
    public void realizarRequisicaoParaAlterarImagemProduto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("a imagem do produto deve ser alterada com sucesso")
    public void imagemProdutoDeveSerAlteradaComSucesso() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
