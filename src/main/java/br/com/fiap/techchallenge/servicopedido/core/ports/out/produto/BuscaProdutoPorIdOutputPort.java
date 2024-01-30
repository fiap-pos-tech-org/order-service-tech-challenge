package br.com.fiap.techchallenge.servicopedido.core.ports.out.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;

public interface BuscaProdutoPorIdOutputPort {

    ProdutoDTO buscarPorId(Long id);
}
