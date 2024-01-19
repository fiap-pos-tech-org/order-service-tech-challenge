package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.lanchonete.utils.ClienteHelper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new CadastraClienteUseCase(clienteRepository);

        clienteSalvo = ClienteHelper.criaCliente();
        clienteDTO = ClienteHelper.criaClienteDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve cadastrar cliente e retornar cliente cadastrado quando dados cadastrais forem corretamente informados")
    void deveCadastrarClienteERetornarClienteCadastrado_QuandoDadosCadastraisForemCorretamenteInformados() {
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
    @DisplayName("Deve lançar BadRequestException quando CPF ou email forem inválidos")
    void deveLancarBadRequestException_QuandoCpfOuEmailForemInvalidos() {
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
    @DisplayName("Deve lançar BadRequestException quando CPF ou email já estiverem em uso")
    void deveLancarBadRequestException_QuandoCpfOuEmailJaEstiveremEmUso() {
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
