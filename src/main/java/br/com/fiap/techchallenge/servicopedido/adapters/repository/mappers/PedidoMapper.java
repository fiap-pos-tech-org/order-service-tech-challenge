package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    private final ItemPedidoMapper itemPedidoMapper;

    public PedidoMapper(ItemPedidoMapper itemPedidoMapper) {
        this.itemPedidoMapper = itemPedidoMapper;
    }

    public Pedido toPedido(PedidoDTO pedidoIn) {
        var pedido = pedidoIn.cliente() != null
                ? new Pedido(pedidoIn.status(), pedidoIn.cliente().id(), pedidoIn.dataCriacao(), pedidoIn.valorTotal())
                : new Pedido(pedidoIn.status(), pedidoIn.dataCriacao(), pedidoIn.valorTotal());

        var itemPedido = itemPedidoMapper.toItemPedido(pedido, pedidoIn.itens());
        pedido.setItens(itemPedido);
        return pedido;
    }

    public PedidoDTO toPedidoDTO(Pedido pedido) {
        var listaItemPedidoOut = itemPedidoMapper.toItemPedidoResponse(pedido.getItens());

        return new PedidoDTO(pedido.getId(), pedido.getClienteId(), listaItemPedidoOut, pedido.getStatus(),
                pedido.getValorTotal(), pedido.getData());
    }

}
