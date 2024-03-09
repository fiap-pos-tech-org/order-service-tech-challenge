package br.com.fiap.techchallenge.servicopedido.core.dtos;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;

import java.math.BigDecimal;

public record MensagemPedidoDTO(Long idPedido, Long idCliente, BigDecimal valorTotal, StatusPedidoEnum status) {
    public MensagemPedidoDTO(Long idPedido, Long idCliente, StatusPedidoEnum status) {
        this(idPedido, idCliente, BigDecimal.ZERO, status);
    }
}
