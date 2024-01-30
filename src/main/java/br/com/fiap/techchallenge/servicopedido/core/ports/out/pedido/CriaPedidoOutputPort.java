package br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;

public interface CriaPedidoOutputPort {
    PedidoDTO criar(PedidoDTO criaPedidoIn);
}
