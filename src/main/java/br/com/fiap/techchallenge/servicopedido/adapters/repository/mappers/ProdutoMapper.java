package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toProduto(ProdutoDTO produtoIn) {
        return new Produto(produtoIn.id(), produtoIn.nome(), produtoIn.categoria(), produtoIn.preco(),
                produtoIn.descricao());
    }

    public ProdutoDTO toProdutoDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getCategoria(), produto.getPreco(),
                produto.getDescricao(), produto.getImagem());
    }

}
