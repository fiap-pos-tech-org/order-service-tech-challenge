package br.com.fiap.techchallenge.servicopedido.core.ports.in.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import java.util.List;

public interface BuscaProdutoPorCategoriaInputPort {

    List<ProdutoDTO> buscarPorCategoria(CategoriaEnum categoriaEnum);
}
