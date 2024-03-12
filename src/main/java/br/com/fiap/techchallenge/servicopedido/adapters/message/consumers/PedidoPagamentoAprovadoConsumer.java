package br.com.fiap.techchallenge.servicopedido.adapters.message.consumers;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoPagamentoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoProducaoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void receiveMessage(@Payload String mensagem) throws JsonProcessingException {
        System.out.printf("Mensagem recebida do serviço pagamento %s\n", mensagem);
        var pedido = mapper.readValue(mensagem, MensagemPedidoPagamentoDTO.class);

        var pedidoAtualizado = atualizaStatusPedidoInputPort.atualizarStatus(pedido.getIdPedido(), StatusPedidoEnum.EM_PREPARACAO);
        var mensagemProducao = new MensagemPedidoProducaoDTO(pedido.getIdPedido(), StatusPedidoEnum.EM_PREPARACAO, pedidoAtualizado.itens());

        publicaPedidoInputPort.publicarFifo(mensagemProducao, topicoProducaoArn);
    }
}
