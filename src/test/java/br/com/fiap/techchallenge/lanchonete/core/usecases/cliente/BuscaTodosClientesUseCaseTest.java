package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

        clienteDTO = ClienteTesteBase.criaDefaultClienteDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

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
        assertTrue(clientes.size() > 0);
        verify(clienteJpaRepository, times(1)).findAll();
    }
}
