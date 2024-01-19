package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Produto;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CriaItemPedidoDTO;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ItemPedidoDTO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ItemPedidoHelper {

    public static ItemPedidoDTO criaItemPedidoDTO() {
        Produto produto = ProdutoHelper.criaProduto();
        return new ItemPedidoDTO(produto.getId(), produto.getNome(), produto.getDescricao(), BigDecimal.valueOf(1L), 1);
    }

    public static List<ItemPedidoDTO> criaListaItemPedidoDTO() {
        return List.of(ItemPedidoHelper.criaItemPedidoDTO());
    }

    public static List<CriaItemPedidoDTO> criaListaCriaItemPedidoDTO() {
        return List.of(new CriaItemPedidoDTO(1L, 1));
    }
}
