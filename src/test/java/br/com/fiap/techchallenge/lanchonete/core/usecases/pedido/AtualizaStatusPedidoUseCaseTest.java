package br.com.fiap.techchallenge.lanchonete.core.usecases.pedido;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.PedidoRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.AtualizaStatusPedidoDTO;
import br.com.fiap.techchallenge.lanchonete.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.lanchonete.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AtualizaStatusPedidoUseCaseTest {

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
    private AtualizaStatusPedidoUseCase pedidoUseCase;
    private Pedido pedidoSalvo;
    private AtualizaStatusPedidoDTO atualizaStatusPedidoDTO;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepository(pedidoMapper, pedidoJpaRepository, clienteMapper, clienteJpaRepository);
        pedidoUseCase = new AtualizaStatusPedidoUseCase(pedidoRepository);

        pedidoSalvo = PedidoHelper.criaPedido();
        atualizaStatusPedidoDTO = PedidoHelper.criaAtualizaStatusPedidoDTO();
    }

    @AfterEach
    void releaseMocks() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Dweve atualizar status do pedido quando dados informados corretamente")
    void deveAtualizarStatusPedido_QuandoDadosInformadosCorretamente() {
        //Arrange
        Long id = 1L;

        when(pedidoJpaRepository.findById(id)).thenReturn(Optional.of(pedidoSalvo));
        when(pedidoJpaRepository.save(pedidoSalvo)).thenReturn(pedidoSalvo);

        //Act
        PedidoDTO pedidoAtualizado = pedidoUseCase.atualizarStatus(id, atualizaStatusPedidoDTO);

        //Assert
        assertThat(pedidoAtualizado).isNotNull();
        assertThat(pedidoAtualizado.status()).isEqualTo(atualizaStatusPedidoDTO.status());
        verify(pedidoJpaRepository, times(1)).findById(anyLong());
        verify(pedidoJpaRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando id informado não existe")
    void deveLancarEntityNotFoundException_QuandoIdIformadoNaoExiste() {
        //Arrange
        Long id = 1L;
        String message = String.format("Pedido %s não encontrado", id);
        when(pedidoJpaRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> pedidoUseCase.atualizarStatus(id, atualizaStatusPedidoDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(pedidoJpaRepository, times(1)).findById(anyLong());
    }
}
