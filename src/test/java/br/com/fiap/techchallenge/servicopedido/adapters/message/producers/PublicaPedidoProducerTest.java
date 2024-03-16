package br.com.fiap.techchallenge.servicopedido.adapters.message.producers;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoProducaoDTO;
import br.com.fiap.techchallenge.servicopedido.utils.ItemPedidoHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class PublicaPedidoProducerTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private SnsClient snsClient;
    @InjectMocks
    private PublicaPedidoProducer publicaPedidoProducer;
    private MensagemPedidoProducaoDTO mensagemPedidoProducao;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        mensagemPedidoProducao = new MensagemPedidoProducaoDTO(1L, StatusPedidoEnum.PRONTO, ItemPedidoHelper.criaListaItemPedidoDTO());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve publicar mensagem em tópico normal quando objeto mensagem e arn do tópico forem informados")
    void devePublicarMensagemEmTopicoNormal_QuandoObjetoMensagemEArnTopicoInformados() throws JsonProcessingException {
        //Arrange
        PublishResponse response = PublishResponse.builder().messageId(UUID.randomUUID().toString()).build();
        when(snsClient.publish(any(PublishRequest.class))).thenReturn(response);
        when(mapper.writeValueAsString(any())).thenReturn(new ObjectMapper().writeValueAsString(mensagemPedidoProducao));

        //Act
        publicaPedidoProducer.publicar(mensagemPedidoProducao, "arn::0001");

        //Assert
        verify(snsClient, times(1)).publish(any(PublishRequest.class));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando objeto mensagem não for serializado")
    void deveLancarRuntimeException_QuandoObjetoMensagemNaoForSerializado() throws JsonProcessingException {
        //Arrange
        when(mapper.writeValueAsString(any(MensagemPedidoProducaoDTO.class))).thenThrow(JsonProcessingException.class);

        //Act
        //Assert
        assertThatThrownBy(() -> publicaPedidoProducer.publicar(mensagemPedidoProducao, "arn::0001"))
                .isInstanceOf(RuntimeException.class);

        verify(snsClient, times(0)).publish(any(PublishRequest.class));
    }

    @Test
    @DisplayName("Deve publicar mensagem em tópico Fifo quando objeto mensagem e ARN do tópico forem informados")
    void devePublicarMensagemEmTopicoFifo_QuandoObjetoMensagemEArnTopicoInformados() throws JsonProcessingException {
        //Arrange
        PublishResponse response = PublishResponse.builder().messageId(UUID.randomUUID().toString()).build();
        when(snsClient.publish(any(PublishRequest.class))).thenReturn(response);
        when(mapper.writeValueAsString(any())).thenReturn(new ObjectMapper().writeValueAsString(mensagemPedidoProducao));

        //Act
        publicaPedidoProducer.publicarFifo(mensagemPedidoProducao, "arn::0001");

        //Assert
        verify(snsClient, times(1)).publish(any(PublishRequest.class));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando objeto mensagem não for serializado topico Fifo")
    void deveLancarRuntimeException_QuandoObjetoMensagemNaoForSerializadoTopicoFifo() throws JsonProcessingException {
        //Arrange
        when(mapper.writeValueAsString(any(MensagemPedidoProducaoDTO.class))).thenThrow(JsonProcessingException.class);

        //Act
        //Assert
        assertThatThrownBy(() -> publicaPedidoProducer.publicarFifo(mensagemPedidoProducao, "arn::0001"))
                .isInstanceOf(RuntimeException.class);

        verify(snsClient, times(0)).publish(any(PublishRequest.class));
    }
}
