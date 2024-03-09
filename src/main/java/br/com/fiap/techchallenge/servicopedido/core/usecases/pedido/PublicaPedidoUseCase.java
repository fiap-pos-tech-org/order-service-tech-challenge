package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoPagamentoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.PublicaPedidoOutputPort;

public class PublicaPedidoUseCase implements PublicaPedidoInputPort {

    PublicaPedidoOutputPort publicaPedido;

    public PublicaPedidoUseCase(PublicaPedidoOutputPort publicaPedido) {
        this.publicaPedido = publicaPedido;
    }

    @Override
    public void publicar(MensagemDTOBase mensagem, String topicoArn) {
        publicaPedido.publicar(mensagem, topicoArn);
    }
}
