package br.com.fiap.techchallenge.servicopedido.core.ports.in.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import java.util.List;

public interface BuscaTodosProdutosInputPort {

    List<ProdutoDTO> buscartodos();
}
