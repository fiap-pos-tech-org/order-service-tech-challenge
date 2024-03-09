package br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoPagamentoDTO;

public interface PublicaPedidoInputPort {
    void publicar(MensagemDTOBase mensagem, String topicoArn);
    void publicarFifo(MensagemDTOBase mensagem, String topicoArn);
}
