package br.com.fiap.techchallenge.servicopedido.adapters.message.producers;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.PublicaPedidoOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Component
public class PublicaPedidoProducer implements PublicaPedidoOutputPort {

    private final SnsClient snsClient;
    private final ObjectMapper mapper;

    public PublicaPedidoProducer(SnsClient snsClient, ObjectMapper mapper) {
        this.snsClient = snsClient;
        this.mapper = mapper;
    }

    @Override
    public void publicar(MensagemPedidoDTO mensagem, String topicoArn) {
        //TODO: Change this shit
        String message;
        try {
            message = mapper.writeValueAsString(mensagem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicoArn)
                .build();

        PublishResponse response = snsClient.publish(request);
        //TODO: Log messageId
    }
}
