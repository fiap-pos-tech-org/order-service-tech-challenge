package br.com.fiap.techchallenge.servicopedido.core.ports.in.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;

public interface CriaProdutoInputPort {

    ProdutoDTO criar(ProdutoDTO produto);
}
