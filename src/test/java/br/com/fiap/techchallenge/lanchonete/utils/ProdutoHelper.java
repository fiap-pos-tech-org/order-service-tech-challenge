package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoHelper {

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

}
