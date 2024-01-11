package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CadastraClienteUseCaseTest {

    @Mock
    private ClienteJpaRepository clienteJpaRepository;
    @Mock
    private ClienteMapper mapper;
    @InjectMocks
    private ClienteRepository clienteRepository;
    private CadastraClienteUseCase clienteUseCase;
    private ClienteDTO clienteDTO;
    private Cliente clienteSalvo;
    private Long id;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new CadastraClienteUseCase(clienteRepository);

        id = 2L;
        clienteSalvo = new Cliente("cliente", "000.000.000-01", "cliente@email.com");
        clienteDTO = new ClienteDTO("cliente1", "000.000.000-07", "cliente1@email.com");
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnClienteStored_WhenPassedRightValues() {
        //Arrange
        when(clienteJpaRepository.save(clienteSalvo)).thenReturn(clienteSalvo);
        when(mapper.toCliente(clienteDTO)).thenReturn(clienteSalvo);
        when(mapper.toClienteDTO(clienteSalvo)).thenReturn(clienteDTO);

        //Act
        ClienteDTO cliente = clienteUseCase.cadastrar(clienteDTO);

        //Assert
        assertNotNull(cliente);
        assertEquals(clienteDTO.nome(), cliente.nome());
        assertEquals(clienteDTO.cpf(), cliente.cpf());
        assertEquals(clienteDTO.email(), cliente.email());
        verify(clienteJpaRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void shouldThrowBadRequestException_WhenCpfOrEmailIsInvalid() {
        //Arrange
        when(clienteJpaRepository.save(any(Cliente.class))).thenThrow(ConstraintViolationException.class);
        when(mapper.toCliente(any(ClienteDTO.class))).thenReturn(new Cliente());

        //Act
        //Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            clienteUseCase.cadastrar(clienteDTO);
        });

        assertEquals("Os campos email ou CPF estão inválidos", exception.getMessage());
        verify(clienteJpaRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void shouldThrowBadRequestException_WhenCpfOrEmailIsAlreadyInUse() {
        //Arrange
        when(clienteJpaRepository.save(any(Cliente.class))).thenThrow(DataIntegrityViolationException.class);
        when(mapper.toCliente(any(ClienteDTO.class))).thenReturn(new Cliente());

        //Act
        //Assert
        Exception exception = assertThrows(BadRequestException.class, () -> {
            clienteUseCase.cadastrar(clienteDTO);
        });

        assertEquals("Os campos email ou CPF já foram cadastrados", exception.getMessage());
        verify(clienteJpaRepository, times(1)).save(any(Cliente.class));
    }
}
