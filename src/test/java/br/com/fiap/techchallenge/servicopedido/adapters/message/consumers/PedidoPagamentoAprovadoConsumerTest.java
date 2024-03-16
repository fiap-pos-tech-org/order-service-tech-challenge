package br.com.fiap.techchallenge.servicopedido.adapters.message.consumers;


import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemPedidoProducaoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidoPagamentoAprovadoConsumerTest {

    @Autowired
    private ObjectMapper mapper;
    @Mock
    private AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    @Mock
    private PublicaPedidoInputPort publicaPedidoInputPort;
    private PedidoPagamentoAprovadoConsumer pedidoPagamentoAprovadoConsumer;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoPagamentoAprovadoConsumer = new PedidoPagamentoAprovadoConsumer(atualizaStatusPedidoInputPort, publicaPedidoInputPort, mapper);
        ReflectionTestUtils.setField(pedidoPagamentoAprovadoConsumer, "topicoProducaoArn", "arn:aws:sns:us-east-1:000000000000:topico_producao.fifo");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve atualizar pedido com status EM_PREPARACAO quando receber mensagem com o pagamento aprovado")
    void deveAtualizarPedidoComStatusAprovado_QuandoReceberMensagemComPagamentoAprovado() {
        //Arrange
        var msgString = "{\"idPedido\": 1, \"idCliente\": 2, \"valorTotal\": 122.3}";
        when(atualizaStatusPedidoInputPort.atualizarStatus(1L, StatusPedidoEnum.EM_PREPARACAO)).thenReturn(PedidoHelper.criaPedidoDTO());
        doNothing().when(publicaPedidoInputPort).publicarFifo(any(MensagemPedidoProducaoDTO.class), anyString());

        //Act
        pedidoPagamentoAprovadoConsumer.receiveMessage(msgString);

        //Assert
        verify(atualizaStatusPedidoInputPort, times(1)).atualizarStatus(anyLong(), any());
        verify(publicaPedidoInputPort, times(1)).publicarFifo(any(MensagemDTOBase.class), anyString());
    }

    @Test
    @DisplayName("Deve lancarJsonProcessingException_QuandoNaoForPossivelSerializarMensagem")
    void deveLancarRuntimeException_QuandoNaoForPossivelSerializarMensagem() {
        //Arrange
        //Act
        assertThatThrownBy(() -> pedidoPagamentoAprovadoConsumer.receiveMessage("{\\}"))
                .isInstanceOf(RuntimeException.class);

        //Assert
        verify(atualizaStatusPedidoInputPort, times(0)).atualizarStatus(anyLong(), any());
        verify(publicaPedidoInputPort, times(0)).publicarFifo(any(MensagemDTOBase.class), anyString());
    }
}
