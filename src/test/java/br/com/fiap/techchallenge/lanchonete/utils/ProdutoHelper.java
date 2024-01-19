package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ProdutoDTO;

import java.math.BigDecimal;

public class ProdutoHelper {

    public static Produto criaProduto() {
        return new Produto(1L, "x-tudo", CategoriaEnum.LANCHE, BigDecimal.valueOf(1L), "muito bom");
    }

    public static ProdutoDTO criaProdutoDTO() {
        return new ProdutoDTO(1L, "x-tudo", CategoriaEnum.LANCHE, BigDecimal.valueOf(1L), "muito bom", null);
    }
}
