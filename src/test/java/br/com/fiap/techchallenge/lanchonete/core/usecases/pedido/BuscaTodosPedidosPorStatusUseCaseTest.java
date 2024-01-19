package br.com.fiap.techchallenge.lanchonete.core.usecases.pedido;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.lanchonete.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BuscaTodosPedidosPorStatusUseCaseTest {
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
    private BuscaTodosPedidosPorStatusUseCase pedidoUseCase;
    private AutoCloseable openMocks;


    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository, clienteMapper, clienteJpaRepository);
        pedidoUseCase = new BuscaTodosPedidosPorStatusUseCase(pedidoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar todos os pedidos por status quando informado um status válido")
    void deveBuscarTodosOsPedidosPorStatus_QuandoInformadoUmStatusValido() {
        //Arrange
        when(pedidoJpaRepository.findByStatus(any(StatusPedidoEnum.class))).thenReturn(PedidoHelper.criaListaPedidos());

        //Act
        List<PedidoDTO> listaPedidos = pedidoUseCase.buscarTodosStatus(StatusPedidoEnum.PENDENTE_DE_PAGAMENTO);

        //Assert
        assertThat(listaPedidos).isNotNull();
        assertThat(listaPedidos).allSatisfy(pedido -> {
            assertThat(pedido).isNotNull();
            assertThat(pedido.cliente()).isNotNull();
            assertThat(pedido.itens()).isNotNull();
            assertThat(pedido.status()).isNotNull();
            assertThat(pedido.valorTotal()).isNotNull();
        });

        verify(pedidoJpaRepository, times(1)).findByStatus(any());
    }
}
