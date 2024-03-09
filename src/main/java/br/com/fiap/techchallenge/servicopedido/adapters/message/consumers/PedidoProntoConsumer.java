package br.com.fiap.techchallenge.servicopedido.adapters.message.consumers;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PedidoProntoConsumer {
    private final ObjectMapper mapper;
    private final AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;

    public PedidoProntoConsumer(ObjectMapper mapper, AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort) {
        this.mapper = mapper;
        this.atualizaStatusPedidoInputPort = atualizaStatusPedidoInputPort;
    }

    @JmsListener(destination = "${aws.sqs.fila-pedido-pronto}")
    public void receiveMessage(@Payload String message) throws JMSException, JsonProcessingException {
        System.out.printf("Mensagem recebida do serviço produçao %s\n", message);
        var pedido = mapper.readValue(message, MensagemPedidoDTO.class);

        atualizaStatusPedidoInputPort.atualizarStatus(pedido.idPedido(), pedido.status());
    }
}
