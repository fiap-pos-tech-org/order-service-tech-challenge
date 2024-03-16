package br.com.fiap.techchallenge.servicopedido.adapters.message.consumers;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoPagamentoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PedidoPagamentoRecusadoConsumer {

    private final Logger logger = LogManager.getLogger(PedidoPagamentoRecusadoConsumer.class);
    private final AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    private final ObjectMapper mapper;

    public PedidoPagamentoRecusadoConsumer(AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort, ObjectMapper mapper) {
        this.atualizaStatusPedidoInputPort = atualizaStatusPedidoInputPort;
        this.mapper = mapper;
    }

    @JmsListener(destination = "${aws.sqs.fila-pagamento-recusado}")
    public void receiveMessage(@Payload String mensagem) {
        logger.info("mensagem recebida do serviço pagamento: {}", mensagem);
        MensagemPedidoPagamentoDTO pedido;
        try {
            pedido = mapper.readValue(mensagem, MensagemPedidoPagamentoDTO.class);
        } catch (JsonProcessingException e) {
            logger.error("erro ao serializar mensagem: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        atualizaStatusPedidoInputPort.atualizarStatus(pedido.getIdPedido(), StatusPedidoEnum.CANCELADO);
    }
}
