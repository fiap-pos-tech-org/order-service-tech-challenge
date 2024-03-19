package br.com.fiap.techchallenge.servicopedido.adapters.message.consumers;


import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.AtualizaStatusPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidoProntoConsumerTest {

    @Autowired
    private ObjectMapper mapper;
    @Mock
    private AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    private PedidoProntoConsumer pedidoProntoConsumer;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoProntoConsumer = new PedidoProntoConsumer(atualizaStatusPedidoInputPort, mapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve atualizar pedido com status RECUSADO quando receber mensagem com o pagamento recusado")
    void deveAtualizarPedidoComStatusCancelado_QuandoReceberMensagemComFormatoCorreto() throws JsonProcessingException {
        //Arrange
        var msgString = "{\"idPedido\": 1, \"status\": \"PRONTO\"}";
        when(atualizaStatusPedidoInputPort.atualizarStatus(1L, StatusPedidoEnum.RECUSADO)).thenReturn(PedidoHelper.criaPedidoDTO());

        //Act
        pedidoProntoConsumer.receiveMessage(msgString);

        //Assert
        verify(atualizaStatusPedidoInputPort, times(1)).atualizarStatus(anyLong(), any());
    }

    @Test
    @DisplayName("Deve lancar RuntimeException quando nao for possivel serializar mensagem")
    void deveLancarRuntimeException_QuandoNaoForPossivelSerializarMensagem() {
        //Arrange
        //Act
        assertThatThrownBy(() -> pedidoProntoConsumer.receiveMessage("{\\}"))
                .isInstanceOf(RuntimeException.class);

        //Assert
        verify(atualizaStatusPedidoInputPort, times(0)).atualizarStatus(anyLong(), any());
    }
}
