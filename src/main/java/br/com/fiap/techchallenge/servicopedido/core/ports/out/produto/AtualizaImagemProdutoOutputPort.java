package br.com.fiap.techchallenge.servicopedido.core.ports.out.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.AtualizaImagemProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;

public interface AtualizaImagemProdutoOutputPort {

    ProdutoDTO atualizar(AtualizaImagemProdutoDTO imagenIn, Long id);
}
