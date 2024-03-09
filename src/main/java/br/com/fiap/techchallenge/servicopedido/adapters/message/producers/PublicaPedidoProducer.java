package br.com.fiap.techchallenge.servicopedido.adapters.message.producers;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.PublicaPedidoOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
public class PublicaPedidoProducer implements PublicaPedidoOutputPort {

    @Value("${aws.sns.group-id}")
    private String messageGroupId;

    private final SnsClient snsClient;
    private final ObjectMapper mapper;

    public PublicaPedidoProducer(SnsClient snsClient, ObjectMapper mapper) {
        this.snsClient = snsClient;
        this.mapper = mapper;
    }

    @Override
    public void publicar(MensagemDTOBase mensagem, String topicoArn) {
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

        snsClient.publish(request);
        System.out.printf("Mensagem publicada %s\n", message);
        //TODO: Log messageId
    }

    @Override
    public void publicarFifo(MensagemDTOBase mensagem, String topicoArn) {
        //TODO: Change this shit
        String message;
        try {
            message = mapper.writeValueAsString(mensagem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .messageGroupId(messageGroupId)
                .messageDeduplicationId(mensagem.getIdPedido().toString())
                .topicArn(topicoArn)
                .build();

        snsClient.publish(request);
        System.out.printf("Mensagem publicada %s\n", message);
    }
}
