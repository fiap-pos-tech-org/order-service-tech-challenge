package br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoDTO;

public interface PublicaPedidoInputPort {
    void publicar(MensagemPedidoDTO mensagem, String topicoArn);
}
