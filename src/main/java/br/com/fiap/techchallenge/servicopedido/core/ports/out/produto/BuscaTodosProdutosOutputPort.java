package br.com.fiap.techchallenge.servicopedido.core.ports.out.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import java.util.List;

public interface BuscaTodosProdutosOutputPort {

    List<ProdutoDTO> buscarTodos();
}
