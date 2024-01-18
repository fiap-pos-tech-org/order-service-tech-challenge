package br.com.fiap.techchallenge.lanchonete;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.ItemPedidoRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.PedidoRequest;

import java.util.List;

public class PedidoTestBase {

    private PedidoTestBase() {
    }

    public static PedidoRequest criarPedidoRequest(Long clientId, Long produtoId) {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setClienteId(clientId);
        pedidoRequest.setItens(criarItensPedidoRequest(produtoId));
        return pedidoRequest;
    }

    private static List<ItemPedidoRequest> criarItensPedidoRequest(Long produtoId) {
        var itemPedidoRequest = new ItemPedidoRequest();
        itemPedidoRequest.setProdutoId(produtoId);
        itemPedidoRequest.setQuantidade(1);
        return List.of(itemPedidoRequest);
    }

}
