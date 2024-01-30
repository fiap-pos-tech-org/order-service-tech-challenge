package br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdInputPort {
    PedidoDTO buscarPorId(Long id);
}
