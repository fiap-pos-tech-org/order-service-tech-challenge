package br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;

public interface CriaPedidoInputPort {
    PedidoDTO criar(CriaPedidoDTO pedidoIn);
}
