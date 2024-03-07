package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.produto.BuscaProdutoPorIdOutputPort;
import br.com.fiap.techchallenge.servicopedido.utils.ClienteHelper;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import br.com.fiap.techchallenge.servicopedido.utils.ProdutoHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CriaPedidoUseCaseTest {

    @Mock
    private BuscaProdutoPorIdOutputPort buscaProdutoPorIdOutputPort;
    @Mock
    private BuscaClienteOutputPort buscaClienteOutputPort;
    @Mock
    private PedidoJpaRepository pedidoJpaRepository;
    @Mock
    private ItemPedidoMapper itemPedidoMapper;
    @InjectMocks
    private PedidoMapper pedidoMapper;
    private PedidoRepository pedidoRepository;

    private CriaPedidoUseCase pedidoUseCase;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository);
        pedidoUseCase = new CriaPedidoUseCase(pedidoRepository, buscaProdutoPorIdOutputPort, buscaClienteOutputPort);
    }

    @Test
    @DisplayName("Deve criar pedido quando todos os atributos forem informados corretamente")
    void deveCriarPedido_QuandoTodosOsAtributosForemInformadosCorretamente() {
        //Arrange
        when(buscaClienteOutputPort.buscar(anyLong())).thenReturn(ClienteHelper.criaClienteDTO());
        when(buscaProdutoPorIdOutputPort.buscarPorId(anyLong())).thenReturn(ProdutoHelper.criaProdutoDTO());
        when(pedidoJpaRepository.save(any(Pedido.class))).thenReturn(PedidoHelper.criaPedido());

        //Act
        PedidoDTO pedidoDTO = pedidoUseCase.criar(PedidoHelper.criaCriaPedidoDTO());

        //Assert
        assertThat(pedidoDTO).satisfies(pedido -> {
            assertThat(pedido).isNotNull();
            assertThat(pedido.id()).isNotNull();
            assertThat(pedido.cliente()).isNotNull();
            assertThat(pedido.itens()).isNotNull();
            assertThat(pedido.status()).isNotNull();
            assertThat(pedido.valorTotal()).isNotNull();
            assertThat(pedido.dataCriacao()).isNotNull();
        });

        verify(buscaClienteOutputPort, times(1)).buscar(anyLong());
        verify(buscaProdutoPorIdOutputPort, times(1)).buscarPorId(anyLong());
        verify(pedidoJpaRepository, times(1)).save(any(Pedido.class));
    }
}
