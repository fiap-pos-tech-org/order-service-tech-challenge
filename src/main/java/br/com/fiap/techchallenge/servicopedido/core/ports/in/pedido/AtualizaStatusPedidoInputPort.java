package br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;

public interface AtualizaStatusPedidoInputPort {
    PedidoDTO atualizarStatus(Long id, StatusPedidoEnum pedidoStatus);
}
