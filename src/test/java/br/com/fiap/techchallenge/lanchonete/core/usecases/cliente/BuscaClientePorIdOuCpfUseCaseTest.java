package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BuscaClientePorIdOuCpfUseCaseTest {

    @Mock
    private ClienteJpaRepository clienteJpaRepository;
    @Mock
    private ClienteMapper mapper;
    @InjectMocks
    private ClienteRepository clienteRepository;
    private BuscaClientePorIdOuCpfUseCase clienteUseCase;
    private ClienteDTO clienteDTO;
    private Cliente clienteSalvo;
    private String cpf;
    private Long id;
    private Long notFoundId;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new BuscaClientePorIdOuCpfUseCase(clienteRepository);


        cpf = "20000000001";
        id = 2L;
        notFoundId = 2000L;
        clienteSalvo = new Cliente("cliente", "000.000.000-01", "cliente@email.com");
        clienteDTO = new ClienteDTO("cliente1", "000.000.000-07", "cliente1@email.com");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldFindCliente_WhenPassedRightCpf() {
        //Arrange
        when(clienteJpaRepository.findByCpf(cpf)).thenReturn(Optional.of(clienteSalvo));
        when(mapper.toClienteDTO(clienteSalvo)).thenReturn(clienteDTO);

        //Act
        ClienteDTO cliente = clienteUseCase.buscar(cpf);

        //Assert
        assertNotNull(cliente);
        verify(clienteJpaRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void shouldThrowEntityNotFoundException_WhenPassedNonExistentCpf() {
        //Arrange
        String message = String.format("Cliente com CPF %s não encontrado", cpf);
        when(clienteJpaRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        //Act
        //Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> clienteUseCase.buscar(cpf));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldFindCliente_WhenPassedRightId() {
        //Arrange
        when(clienteJpaRepository.findById(id)).thenReturn(Optional.of(clienteSalvo));
        when(mapper.toClienteDTO(clienteSalvo)).thenReturn(clienteDTO);

        //Act
        ClienteDTO cliente = clienteUseCase.buscar(id);

        //Assert
        assertNotNull(cliente);
        verify(clienteJpaRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowEntityNotFoundException_WhenPassedNonExistentId() {
        //Arrange
        String message = String.format("Cliente com id %s não encontrado", notFoundId);
        when(clienteJpaRepository.findById(notFoundId)).thenReturn(Optional.empty());

        //Act
        //Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> clienteUseCase.buscar(notFoundId));
        assertEquals(message, exception.getMessage());
    }
}
