package br.com.fiap.techchallenge.lanchonete.core.usecases.pedido;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.lanchonete.utils.PedidoHelper;
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
    private ClienteJpaRepository clienteJpaRepository;
    @Mock
    private ClienteMapper clienteMapper;
    @Mock
    private ItemPedidoMapper itemPedidoMapper;
    @InjectMocks
    private PedidoMapper pedidoMapper;
    private PedidoRepository pedidoRepository;
    private BuscarPedidoPorIdUseCase pedidoUseCase;
    private AutoCloseable openMocks;


    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository, clienteMapper, clienteJpaRepository);
        pedidoUseCase = new BuscarPedidoPorIdUseCase(pedidoRepository);
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

        //Act
        PedidoDTO pedido = pedidoUseCase.buscarPorId(id);

        //Assert
        assertThat(pedido).satisfies(p -> {
            assertThat(p.status()).isEqualTo(StatusPedidoEnum.PENDENTE_DE_PAGAMENTO);
            assertThat(p.cliente()).isNotNull();
            assertThat(p.valorTotal()).isEqualTo(BigDecimal.valueOf(1L));
        });

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
