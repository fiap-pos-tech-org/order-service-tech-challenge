package br.com.fiap.techchallenge.servicopedido.core.usecases.cliente;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.utils.ClienteHelper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class BuscaTodosClientesUseCaseTest {

    @Mock
    private ClienteJpaRepository clienteJpaRepository;
    @Mock
    private ClienteMapper mapper;
    @InjectMocks
    private ClienteRepository clienteRepository;
    private BuscaTodosClientesUseCase clienteUseCase;
    private ClienteDTO clienteDTO;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new BuscaTodosClientesUseCase(clienteRepository);

        clienteDTO = ClienteHelper.criaClienteDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    @DisplayName("Busca todos os clientes")
    class BuscaTodosCliente {
        @Test
        @DisplayName("Deve buscar todos os clientes")
        void deveBuscarTodosOsClientes_QuandoOMetodoBuscarTodosForInvocado() {
            //Arrange
            List<Cliente> clientesRetornados = Collections.singletonList(new Cliente());
            List<ClienteDTO> clientesDTO = Collections.singletonList(clienteDTO);

            when(clienteJpaRepository.findAll()).thenReturn(clientesRetornados);
            when(mapper.toClienteListDTO(clientesRetornados)).thenReturn(clientesDTO);

            //Act
            List<ClienteDTO> clientes = clienteUseCase.buscarTodos();

            //Assert
            assertThat(clientes).isNotNull();
            assertThat(clientes).isNotEmpty();
            assertThat(clientes).hasAtLeastOneElementOfType(ClienteDTO.class);

            verify(clienteJpaRepository, times(1)).findAll();
        }

    }
}
