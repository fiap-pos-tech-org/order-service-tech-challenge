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
public class PedidoPagamentoCanceladoConsumerTest {

    @Autowired
    private ObjectMapper mapper;
    @Mock
    private AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    private PedidoPagamentoCanceladoConsumer pedidoPagamentoCanceladoConsumer;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoPagamentoCanceladoConsumer = new PedidoPagamentoCanceladoConsumer(atualizaStatusPedidoInputPort, mapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve atualizar pedido com status CANCELADO quando receber mensagem com o pagamento cancelado")
    void deveAtualizarPedidoComStatusCancelado_QuandoReceberMensagemComFormatoCorreto() throws JsonProcessingException {
        //Arrange
        var msgString = "{\"idPedido\": 1, \"idCliente\": 2, \"valorTotal\": 122.3}";
        when(atualizaStatusPedidoInputPort.atualizarStatus(1L, StatusPedidoEnum.CANCELADO)).thenReturn(PedidoHelper.criaPedidoDTO());

        //Act
        pedidoPagamentoCanceladoConsumer.receiveMessage(msgString);

        //Assert
        verify(atualizaStatusPedidoInputPort, times(1)).atualizarStatus(anyLong(), any());
    }

    @Test
    @DisplayName("Deve lancar RuntimeException quando nao for possivel serializar mensagem")
    void deveLancarRuntimeException_QuandoNaoForPossivelSerializarMensagem() {
        //Arrange
        //Act
        assertThatThrownBy(() -> pedidoPagamentoCanceladoConsumer.receiveMessage("{\\}"))
                .isInstanceOf(RuntimeException.class);

        //Assert
        verify(atualizaStatusPedidoInputPort, times(0)).atualizarStatus(anyLong(), any());
    }
}
