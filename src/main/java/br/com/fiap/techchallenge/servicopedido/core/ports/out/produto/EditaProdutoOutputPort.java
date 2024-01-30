package br.com.fiap.techchallenge.servicopedido.core.ports.out.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
public interface EditaProdutoOutputPort {

    ProdutoDTO editar(ProdutoDTO produto, Long id);
}
