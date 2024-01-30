package br.com.fiap.techchallenge.servicopedido.core.ports.out.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import java.util.List;

public interface BuscaProdutoPorCategoriaOutputPort {

    List<ProdutoDTO> buscarPorCategoria(CategoriaEnum categoriaEnum);
}
