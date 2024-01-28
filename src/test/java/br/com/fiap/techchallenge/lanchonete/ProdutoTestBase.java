package br.com.fiap.techchallenge.lanchonete;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.ProdutoRequest;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.CategoriaEnum;

import java.math.BigDecimal;

public class ProdutoTestBase {

    private ProdutoTestBase() {
    }

    public static ProdutoRequest criarProdutoRequest() {
        var produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("X-Tudo");
        produtoRequest.setPreco(BigDecimal.valueOf(10.5));
        produtoRequest.setDescricao("X-Tudo Monstrão");
        produtoRequest.setCategoria(CategoriaEnum.LANCHE);
        return produtoRequest;
    }

}
