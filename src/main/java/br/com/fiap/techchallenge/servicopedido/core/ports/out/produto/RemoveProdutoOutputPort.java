package br.com.fiap.techchallenge.servicopedido.core.ports.out.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;

public interface RemoveProdutoOutputPort {

    ProdutoDTO remover(Long id);
}
