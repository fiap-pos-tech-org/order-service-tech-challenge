package br.com.fiap.techchallenge.lanchonete.core.usecases.pedido;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.lanchonete.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.pedido.BuscaTodosPedidosOutputPort;
import br.com.fiap.techchallenge.lanchonete.utils.PedidoHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BuscaPedidosPorPrioridadeUseCaseTest {

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
    private BuscaPedidosPorPrioridadeUseCase pedidoUseCase;
    private AutoCloseable openMocks;


    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository, clienteMapper, clienteJpaRepository);
        pedidoUseCase = new BuscaPedidosPorPrioridadeUseCase(pedidoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    @DisplayName("Deve buscar todos os pedidos quando o método buscarTodos for invocado")
    void deveBuscarTodosOsPedidos_QuandoMetodoBuscarTodosForInvocado() {
        //Arrange
        when(pedidoJpaRepository.findAll()).thenReturn(PedidoHelper.criaListaPedidos());

        //Act
        List<PedidoDTO> listaPedidos = pedidoUseCase.buscarPorPrioridade();

        //Assert
        assertThat(listaPedidos).isNotNull();
        assertThat(listaPedidos).allSatisfy(pedido -> {
            assertThat(pedido).isNotNull();
            assertThat(pedido.id()).isNotNull();
            assertThat(pedido.cliente()).isNotNull();
            assertThat(pedido.itens()).isNotNull();
            assertThat(pedido.status()).isNotNull();
            assertThat(pedido.valorTotal()).isNotNull();
        });

        verify(pedidoJpaRepository, times(1)).findAll();
    }
}
