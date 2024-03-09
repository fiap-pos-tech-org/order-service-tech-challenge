package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.PublicaPedidoOutputPort;

public class PublicaPedidoUseCase implements PublicaPedidoInputPort {

    PublicaPedidoOutputPort publicaPedido;

    public PublicaPedidoUseCase(PublicaPedidoOutputPort publicaPedido) {
        this.publicaPedido = publicaPedido;
    }

    @Override
    public void publicar(MensagemPedidoDTO mensagem, String topicoArn) {
        publicaPedido.publicar(mensagem, topicoArn);
    }
}
