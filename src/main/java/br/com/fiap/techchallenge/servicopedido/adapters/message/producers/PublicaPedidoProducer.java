package br.com.fiap.techchallenge.servicopedido.adapters.message.producers;

import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.PublicaPedidoOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Component
public class PublicaPedidoProducer implements PublicaPedidoOutputPort {

    private final Logger logger = LogManager.getLogger(PublicaPedidoProducer.class);

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
        String message;
        try {
            message = mapper.writeValueAsString(mensagem);
        } catch (JsonProcessingException e) {
            logger.error("erro ao serializar mensagem: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicoArn)
                .build();

        PublishResponse response = snsClient.publish(request);
        logger.info("mensagem com id {} publicada com sucesso", response.messageId());
    }

    @Override
    public void publicarFifo(MensagemDTOBase mensagem, String topicoArn) {
        String message;
        try {
            message = mapper.writeValueAsString(mensagem);
        } catch (JsonProcessingException e) {
            logger.error("erro ao serializar mensagem: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .messageGroupId(messageGroupId)
                .messageDeduplicationId(mensagem.getIdPedido().toString())
                .topicArn(topicoArn)
                .build();

        PublishResponse response = snsClient.publish(request);
        logger.info("mensagem com id {} publicada com sucesso", response.messageId());
    }
}
