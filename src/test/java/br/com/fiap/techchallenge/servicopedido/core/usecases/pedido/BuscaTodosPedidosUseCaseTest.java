package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.utils.ClienteHelper;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.PublicaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BuscaTodosPedidosUseCaseTest {

    @Mock
    private PedidoJpaRepository pedidoJpaRepository;
    @Mock
    private PublicaPedidoInputPort publicaPedidoInputPort;
    @Mock
    private ItemPedidoMapper itemPedidoMapper;
    @Mock
    BuscaClienteOutputPort buscaClienteOutputPort;
    @InjectMocks
    private PedidoMapper pedidoMapper;
    private PedidoRepository pedidoRepository;
    private BuscaTodosPedidosUseCase pedidoUseCase;
    private AutoCloseable openMocks;


    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository, publicaPedidoInputPort);
        pedidoUseCase = new BuscaTodosPedidosUseCase(pedidoRepository, buscaClienteOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveBuscarTodosOsPedidos_QuandoMetodoBuscarTodosForInvocado() {
        //Arrange
        when(pedidoJpaRepository.findAll()).thenReturn(PedidoHelper.criaListaPedidos());
        when(buscaClienteOutputPort.buscar(anyLong())).thenReturn(ClienteHelper.criaClienteDTO());

        //Act
        List<PedidoDTO> listaPedidos = pedidoUseCase.buscarTodos();

        //Assert
        assertThat(listaPedidos).isNotNull().allSatisfy(pedido -> {
            assertThat(pedido).isNotNull();
            assertThat(pedido.cliente()).isNotNull();
            assertThat(pedido.itens()).isNotNull();
            assertThat(pedido.status()).isNotNull();
            assertThat(pedido.valorTotal()).isNotNull();
        });

        verify(pedidoJpaRepository, times(1)).findAll();
        verify(buscaClienteOutputPort, times(1)).buscar(anyLong());
    }
}
