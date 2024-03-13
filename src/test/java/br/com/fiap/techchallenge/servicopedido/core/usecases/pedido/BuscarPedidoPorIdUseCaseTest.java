package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.adapters.gateways.ClienteGateway;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.utils.ClienteHelper;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class BuscarPedidoPorIdUseCaseTest {

    @Mock
    private PedidoJpaRepository pedidoJpaRepository;
    @Mock
    private PublicaPedidoInputPort publicaPedidoInputPort;
    @Mock
    private ItemPedidoMapper itemPedidoMapper;
    @Mock
    private BuscaClienteOutputPort buscaClienteOutputPort;
    @InjectMocks
    private PedidoMapper pedidoMapper;
    private PedidoRepository pedidoRepository;
    private BuscarPedidoPorIdUseCase pedidoUseCase;
    private AutoCloseable openMocks;


    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository, publicaPedidoInputPort);
        pedidoUseCase = new BuscarPedidoPorIdUseCase(pedidoRepository, buscaClienteOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar um pedido quando informado um id válido")
    void deveBuscarPedido_QuandoInformadoUmIdValido() {
        //Arrange
        Long id = 1L;
        when(pedidoJpaRepository.findById(any())).thenReturn(Optional.of(PedidoHelper.criaPedido()));
        when(buscaClienteOutputPort.buscar(anyLong())).thenReturn(ClienteHelper.criaClienteDTO());

        //Act
        PedidoDTO pedido = pedidoUseCase.buscarPorId(id);

        //Assert
        assertThat(pedido).satisfies(p -> {
            assertThat(p.status()).isEqualTo(StatusPedidoEnum.PENDENTE_DE_PAGAMENTO);
            assertThat(p.cliente()).isNotNull();
            assertThat(p.valorTotal()).isEqualTo(BigDecimal.valueOf(1L));
        });

        verify(buscaClienteOutputPort, times(1)).buscar(anyLong());
        verify(pedidoJpaRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando informado um id inválido")
    void deveLancarEntityNotFoundException_QuandoInformadoUmIdIValido() {
        //Arrange
        Long id = 1L;
        String message = String.format("Pedido %s não encontrado", id);
        when(pedidoJpaRepository.findById(any())).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> pedidoUseCase.buscarPorId(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(pedidoJpaRepository, times(1)).findById(any());
    }
}
