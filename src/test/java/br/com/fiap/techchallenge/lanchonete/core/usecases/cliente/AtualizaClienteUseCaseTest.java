package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AtualizaClienteUseCaseTest {

    @Mock
    private ClienteJpaRepository clienteJpaRepository;
    @Mock
    private ClienteMapper mapper;
    @InjectMocks
    private ClienteRepository clienteRepository;
    private AtualizaClienteUseCase clienteUseCase;
    private ClienteDTO clienteDTO;
    private Cliente clienteSalvo;
    private Cliente clienteAtualizado;
    private Long id;
    private Long notFoundId;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new AtualizaClienteUseCase(clienteRepository);

        id = 2L;
        notFoundId = 2000L;
        clienteSalvo = new Cliente("cliente", "000.000.000-01", "cliente@email.com");
        clienteDTO = new ClienteDTO("cliente1", "000.000.000-07", "cliente1@email.com");
        clienteAtualizado = new Cliente("cliente1", "000.000.000-07", "cliente1@email.com");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldUpdateCliente_WhenPassedRightDataAndExistentId() {
        //Arrange
        when(clienteJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteSalvo));
        when(clienteJpaRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);
        when(mapper.toClienteDTO(any(Cliente.class))).thenReturn(clienteDTO);

        //Act
        var clienteSalvoAtualizado = clienteUseCase.atualizar(clienteDTO, id);

        //Assert
        assertEquals(clienteDTO.cpf(), clienteSalvoAtualizado.cpf());
        assertEquals(clienteDTO.email(), clienteSalvoAtualizado.email());
        assertEquals(clienteDTO.nome(), clienteSalvoAtualizado.nome());
        verify(clienteJpaRepository, times(1)).findById(any(Long.class));
        verify(clienteJpaRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void shouldReturnEntityNotFoundException_WhenNonExistentId() {
        //Arrange
        when(clienteJpaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //Act
        String exceptionMessage = String.format("Cliente com Id %s não encontrado", notFoundId);

        //Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            clienteUseCase.atualizar(clienteDTO, notFoundId);
        });
        assertEquals(exceptionMessage, exception.getMessage());
        verify(clienteJpaRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void shouldReturnBadRequestException_WhenDataIsAlreadyStored() {
        //Arrange
        when(clienteJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteSalvo));
        when(clienteJpaRepository.save(any(Cliente.class))).thenThrow(DataIntegrityViolationException.class);

        //Act
        String exceptionMessage = "Os campos email ou CPF já foram cadastrados";

        //Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            clienteUseCase.atualizar(clienteDTO, id);
        });
        assertEquals(exceptionMessage, exception.getMessage());
        verify(clienteJpaRepository, times(1)).findById(any(Long.class));
    }
}
