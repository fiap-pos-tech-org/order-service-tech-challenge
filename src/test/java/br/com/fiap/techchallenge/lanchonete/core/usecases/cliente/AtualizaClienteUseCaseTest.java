package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.ClienteRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.lanchonete.utils.ClienteHelper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private final Long ID = 2L;
    private final Long NOT_FOUND_ID = 2000L;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteUseCase = new AtualizaClienteUseCase(clienteRepository);

        clienteSalvo = ClienteHelper.criaCliente();
        clienteDTO = ClienteHelper.criaClienteDTO();
        clienteAtualizado = ClienteHelper.criaCopiaClienteDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    @DisplayName("Atualiza cliente")
    class AtualizaCliente {
        @Test
        @DisplayName("Deve atualizar um cliente quando o cliente existir e informados os dados corretamente")
        void deveAtualizarCliente_QuandoOClienteExistirEDadosCorretamenteInformados() {
            //Arrange
            when(clienteJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteSalvo));
            when(clienteJpaRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);
            when(mapper.toClienteDTO(any(Cliente.class))).thenReturn(clienteDTO);

            //Act
            var clienteSalvoAtualizado = clienteUseCase.atualizar(clienteDTO, ID);

            //Assert
            assertThat(clienteSalvoAtualizado).satisfies(cliente -> {
                assertEquals(clienteDTO.cpf(), cliente.cpf());
                assertEquals(clienteDTO.email(), cliente.email());
                assertEquals(clienteDTO.nome(), cliente.nome());
            });
            verify(clienteJpaRepository, times(1)).findById(any(Long.class));
            verify(clienteJpaRepository, times(1)).save(any(Cliente.class));
        }

        @Test
        @DisplayName("Deve lançar EntityNotFoundException quando um Id não cadastrado for informado")
        void deveLancarEntityNotFoundException_QuandoUmIdNaoCadastradoForInformado() {
            //Arrange
            when(clienteJpaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            //Act
            String exceptionMessage = String.format("Cliente com Id %s não encontrado", NOT_FOUND_ID);

            //Assert
            assertThatThrownBy(() -> clienteUseCase.atualizar(clienteDTO, NOT_FOUND_ID))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(exceptionMessage);

            verify(clienteJpaRepository, times(1)).findById(any(Long.class));
        }

        @Test
        @DisplayName("Deve lançar BadRequestException quando um CPF inválido for informado")
        void deveLancarBadRequestException_QuandoCpfOuEmailJaEstiveremEmUso() {
            //Arrange
            String exceptionMessage = "Os campos email ou CPF já foram cadastrados";
            when(clienteJpaRepository.findById(any(Long.class))).thenReturn(Optional.of(clienteSalvo));
            when(clienteJpaRepository.save(any(Cliente.class))).thenThrow(DataIntegrityViolationException.class);

            //Act
            //Assert
            assertThatThrownBy(() -> clienteUseCase.atualizar(clienteDTO, ID))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(exceptionMessage);

            verify(clienteJpaRepository, times(1)).findById(any(Long.class));
        }
    }
}
