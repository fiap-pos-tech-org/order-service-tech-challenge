package br.com.fiap.techchallenge.servicopedido.adapters.message.consumers;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PedidoPagamentoAprovadoConsumer {

    @Value("${aws.sns.topico-producao-arn}")
    private String topicoProducaoArn;
    private final AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    private final PublicaPedidoInputPort publicaPedidoInputPort;
    private final ObjectMapper mapper;

    public PedidoPagamentoAprovadoConsumer(AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort,
                                           PublicaPedidoInputPort publicaPedidoInputPort, ObjectMapper mapper) {
        this.atualizaStatusPedidoInputPort = atualizaStatusPedidoInputPort;
        this.publicaPedidoInputPort = publicaPedidoInputPort;
        this.mapper = mapper;
    }

    @Transactional
    @JmsListener(destination = "${aws.sqs.fila-pagamento-aprovado}")
    public void receiveMessage(@Payload String message) throws JMSException, JsonProcessingException {
        System.out.printf("Mensagem recebida do serviço pagamento %s\n", message);
        var pedido = mapper.readValue(message, MensagemPedidoDTO.class);

        var pedidoAtualizado = atualizaStatusPedidoInputPort.atualizarStatus(pedido.idPedido(), pedido.status());
        var mensagemDto = new MensagemPedidoDTO(pedidoAtualizado.id(), pedidoAtualizado.cliente().id(), pedidoAtualizado.status());
        publicaPedidoInputPort.publicar(mensagemDto, topicoProducaoArn);
    }
}
