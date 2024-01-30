package br.com.fiap.techchallenge.servicopedido.core.usecases.produto;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.produto.BuscaProdutoPorCategoriaInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.produto.BuscaProdutoPorCategoriaOutputPort;

import java.util.List;

public class BuscaProdutoPorCategoriaUseCase implements BuscaProdutoPorCategoriaInputPort {

    BuscaProdutoPorCategoriaOutputPort buscaProdutoPorIdOutputPort;

    public BuscaProdutoPorCategoriaUseCase(BuscaProdutoPorCategoriaOutputPort buscaProdutoPorIdOutputPort) {
        this.buscaProdutoPorIdOutputPort = buscaProdutoPorIdOutputPort;
    }

    @Override
    public List<ProdutoDTO> buscarPorCategoria(CategoriaEnum categoriaEnum) {
        return buscaProdutoPorIdOutputPort.buscarPorCategoria(categoriaEnum);
    }
}
