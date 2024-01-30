package br.com.fiap.techchallenge.servicopedido.core.ports.in.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.AtualizaImagemProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;

public interface AtualizaImagemProdutoInputPort {

    ProdutoDTO atualizar(AtualizaImagemProdutoDTO imagemIn, Long id);
}
