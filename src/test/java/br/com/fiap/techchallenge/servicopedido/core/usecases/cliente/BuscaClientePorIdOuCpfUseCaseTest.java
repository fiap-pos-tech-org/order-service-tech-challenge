package br.com.fiap.techchallenge.servicopedido.core.usecases.cliente;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.utils.ClienteHelper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


public class BuscaClientePorIdOuCpfUseCaseTest {

    @Mock
    private ClienteJpaRepository clienteJpaRepository;
    @Mock
    private ClienteMapper mapper;
    @InjectMocks
    private ClienteRepository clienteRepository;
    private BuscaClientePorIdOuCpfIdOuCpfUseCase clienteUseCase;
    private ClienteDTO clienteDTO;
    private Cliente clienteSalvo;
    private final String CPF = "20000000001";
    private final Long ID = 2L;
    private final Long NOT_FOUND_ID = 2000L;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new BuscaClientePorIdOuCpfIdOuCpfUseCase(clienteRepository);

        clienteSalvo = ClienteHelper.criaCliente();
        clienteDTO = ClienteHelper.criaClienteDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    @DisplayName("Busca cliente")
    class BuscaClienteIdCpf {
        @Test
        @DisplayName("Deve buscar cliente quando informado um CPF válido")
        void deveBuscarCliente_QuandoInformadoUmCpfValido() {
            //Arrange
            when(clienteJpaRepository.findByCpf(CPF)).thenReturn(Optional.of(clienteSalvo));
            when(mapper.toClienteDTO(clienteSalvo)).thenReturn(clienteDTO);

            //Act
            ClienteDTO cliente = clienteUseCase.buscar(CPF);

            //Assert
            assertThat(cliente).isNotNull();
            verify(clienteJpaRepository, times(1)).findByCpf(CPF);
        }

        @Test
        @DisplayName("Deve lançar EntityNotFoundException quando um CPF não cadastrado for informado")
        void deveLancarEntityNotFoundException_QuandoUmCpfNaoCadastradoForInformado() {
            //Arrange
            String message = String.format("Cliente com CPF %s não encontrado", CPF);
            when(clienteJpaRepository.findByCpf(CPF)).thenReturn(Optional.empty());

            //Act
            //Assert
            assertThatThrownBy(() -> clienteUseCase.buscar(CPF))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(message);
        }

        @Test
        @DisplayName("Deve buscar cliente quando informado um ID válido")
        void deveBuscarCliente_QuandoInformadoUmIdValido() {
            //Arrange
            when(clienteJpaRepository.findById(ID)).thenReturn(Optional.of(clienteSalvo));
            when(mapper.toClienteDTO(clienteSalvo)).thenReturn(clienteDTO);

            //Act
            ClienteDTO cliente = clienteUseCase.buscar(ID);

            //Assert
            assertThat(cliente).isNotNull();
            verify(clienteJpaRepository, times(1)).findById(ID);
        }

        @Test
        @DisplayName("Deve lançar EntityNotFoundException quando um ID não cadastrado for informado")
        void deveLancarEntityNotFoundException_QuandoUmIdNaoCadastradoForInformado() {
            //Arrange
            String message = String.format("Cliente com id %s não encontrado", NOT_FOUND_ID);
            when(clienteJpaRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

            //Act
            //Assert
            assertThatThrownBy(() -> clienteUseCase.buscar(NOT_FOUND_ID))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(message);
        }
    }
}
