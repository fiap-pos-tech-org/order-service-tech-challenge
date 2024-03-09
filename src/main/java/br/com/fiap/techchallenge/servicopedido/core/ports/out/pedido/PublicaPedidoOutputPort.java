package br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoDTO;

public interface PublicaPedidoOutputPort {
    void publicar(MensagemPedidoDTO mensagem, String topicoArn);
}
