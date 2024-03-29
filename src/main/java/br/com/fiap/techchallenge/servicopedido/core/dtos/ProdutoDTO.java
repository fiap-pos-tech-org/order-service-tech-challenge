package br.com.fiap.techchallenge.servicopedido.core.dtos;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;

import java.math.BigDecimal;

public record ProdutoDTO(Long id, String nome, CategoriaEnum categoria, BigDecimal preco, String descricao, byte[] imagem) {

    public ProdutoDTO(String nome, CategoriaEnum categoria, BigDecimal preco, String descricao) {
        this(null, nome, categoria, preco, descricao,null);
    }
}
