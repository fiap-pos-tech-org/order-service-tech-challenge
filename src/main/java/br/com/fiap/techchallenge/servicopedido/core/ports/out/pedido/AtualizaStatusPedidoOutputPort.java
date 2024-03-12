package br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;

public interface AtualizaStatusPedidoOutputPort {
    PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status);
}
