package br.com.fiap.techchallenge.servicopedido.adapters.web.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component("ProdutoMapperWeb")
public class ProdutoMapper {

    public ProdutoResponse toProdutoResponse(ProdutoDTO produtoOut) {
        return new ProdutoResponse(produtoOut.id(), produtoOut.nome(), produtoOut.categoria(),
                produtoOut.preco(), produtoOut.descricao(), produtoOut.imagem());
    }

}
