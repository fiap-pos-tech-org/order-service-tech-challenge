package br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdOutputPort {

    PedidoDTO buscarPorId(Long id);
}
