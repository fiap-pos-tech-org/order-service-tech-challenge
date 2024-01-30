package br.com.fiap.techchallenge.servicopedido.utils;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests.ProdutoRequest;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoHelper {

    private ProdutoHelper() {
    }

    public static Produto criaProduto() {
        return new Produto(1L, "x-tudo", CategoriaEnum.LANCHE, BigDecimal.valueOf(1L), "muito bom");
    }

    public static ProdutoDTO criaProdutoDTO() {
        return new ProdutoDTO("dogão-brabo", CategoriaEnum.LANCHE, BigDecimal.valueOf(6L), "topperson");
    }

    public static Produto criaProdutoCopiaDTO() {
        ProdutoDTO produtoDTO = criaProdutoDTO();
        return new Produto(
                1L,
                produtoDTO.nome(),
                produtoDTO.categoria(),
                produtoDTO.preco(),
                produtoDTO.descricao()
        );
    }

    public static List<Produto> criaListaProdutos() {
        return List.of(ProdutoHelper.criaProduto());
    }

    public static ProdutoResponse criaProdutoResponse() {
        return new ProdutoResponse(
                1L,
                "x-tudo",
                CategoriaEnum.LANCHE,
                BigDecimal.valueOf(1L),
                "muito bom",
                new byte[]{1, 2}
        );
    }

    public static List<ProdutoDTO> criaListaProdutoDTO() {
        return List.of(ProdutoHelper.criaProdutoDTO());
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
