package br.com.fiap.techchallenge.lanchonete.adapters.web.controllers;

import br.com.fiap.techchallenge.lanchonete.adapters.web.PedidoController;
import br.com.fiap.techchallenge.lanchonete.adapters.web.handlers.ExceptionsHandler;
import br.com.fiap.techchallenge.lanchonete.adapters.web.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.web.mappers.PedidoMapper;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.lanchonete.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cobranca.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.pedido.*;
import br.com.fiap.techchallenge.lanchonete.utils.CobrancaHelper;
import br.com.fiap.techchallenge.lanchonete.utils.ObjectParaJsonMapper;
import br.com.fiap.techchallenge.lanchonete.utils.PedidoHelper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PedidoControllerTest {
    private MockMvc mockMvc;
    private AutoCloseable openMocks;
    @Mock
    private CriaPedidoInputPort criaPedidoInputPort;
    @Mock
    private AtualizaStatusPedidoInputPort atualizaStatusPedidoInputPort;
    @Mock
    private BuscaTodosPedidosInputPort buscaTodosPedidosInputPort;
    @Mock
    private BuscaPedidosOrdenadosPorPrioridadeInputPort buscaPedidosOrdenadosPorPrioridadeInputPort;
    @Mock
    private BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort;
    @Mock
    private BuscaTodosPedidosPorStatusInputPort buscaTodosPedidosPorStatusInputPort;
    @Mock
    private BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort;
    @Mock
    private PedidoMapper pedidoMapperWeb;
    @Mock
    private CobrancaMapper cobrancaMapper;
    private PedidoController pedidoController;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoController = new PedidoController(
                criaPedidoInputPort,
                atualizaStatusPedidoInputPort,
                buscaTodosPedidosInputPort,
                buscaPedidosOrdenadosPorPrioridadeInputPort,
                buscarPedidoPorIdInputPort,
                buscaTodosPedidosPorStatusInputPort,
                buscaCobrancaPorPedidoIdInputPort,
                pedidoMapperWeb,
                cobrancaMapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController)
                .setControllerAdvice(new ExceptionsHandler())
                .build();
    }

    @AfterEach
    void releaseMocks() throws Exception {
        openMocks.close();
    }

    @Nested
    @DisplayName("Cadastra um pedido")
    class CadastraPedido {

        @Test
        @DisplayName("Deve criar um pedido quando os dados corretamente informados")
        void deveCriarPedido_QuandoDadosCorretamenteInformados() throws Exception {
            //Arrange
            when(criaPedidoInputPort.criar(any(CriaPedidoDTO.class))).thenReturn(PedidoHelper.criaPedidoDTO());
            when(pedidoMapperWeb.toPedidoResponse(any())).thenReturn(PedidoHelper.criaPedidoResponse());

            //Act
            //Assert
            mockMvc.perform(post("/pedidos")
                            .contentType("application/json")
                            .content(ObjectParaJsonMapper.converte(PedidoHelper.criaPedidoRequest())))
                    .andExpect(status().isCreated())
                    .andExpectAll(
                            jsonPath("$.id").value(1),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.clienteNome").value("Cliente"),
                            jsonPath("$.clienteNome").isString(),
                            jsonPath("$.itens").isNotEmpty(),
                            jsonPath("$.itens").isArray(),
                            jsonPath("$.status").value("PENDENTE_DE_PAGAMENTO"),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valorTotal").value(1),
                            jsonPath("$.valorTotal").isNumber(),
                            jsonPath("$.data").isNotEmpty(),
                            jsonPath("$.data").isArray()
                    );
        }
    }

    @Nested
    @DisplayName("Busca pedidos")
    class BuscaPedidos {
        @Test
        @DisplayName("Deve retornar todos os pedidos")
        void deveRetornarTodosPedidos() throws Exception {
            //Arrange
            when(buscaTodosPedidosInputPort.buscarTodos()).thenReturn(PedidoHelper.criaListaPedidoDTO());
            when(pedidoMapperWeb.toPedidoListResponse(anyList())).thenReturn(PedidoHelper.criaListaPedidoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/pedidos"))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$").isNotEmpty(),
                            jsonPath("$").isArray(),
                            jsonPath("$[0].id").value(1),
                            jsonPath("$[0].id").isNumber(),
                            jsonPath("$[0].clienteNome").value("Cliente"),
                            jsonPath("$[0].clienteNome").isString(),
                            jsonPath("$[0].itens").isNotEmpty(),
                            jsonPath("$[0].itens").isArray(),
                            jsonPath("$[0].status").value("PENDENTE_DE_PAGAMENTO"),
                            jsonPath("$[0].status").isString(),
                            jsonPath("$[0].valorTotal").value(1),
                            jsonPath("$[0].valorTotal").isNumber(),
                            jsonPath("$[0].data").isNotEmpty(),
                            jsonPath("$[0].data").isArray()
                    );
        }

        @Test
        @DisplayName("Deve retornar todos os pedidos por status")
        void deveRetornarTodosPedidosPorStatus() throws Exception {
            //Arrange
            when(buscaTodosPedidosPorStatusInputPort.buscarTodosStatus(any(StatusPedidoEnum.class))).thenReturn(PedidoHelper.criaListaPedidoDTO());
            when(pedidoMapperWeb.toPedidoResponse(any(PedidoDTO.class))).thenReturn(PedidoHelper.criaPedidoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/pedidos/status/{status}", "PENDENTE_DE_PAGAMENTO"))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$").isNotEmpty(),
                            jsonPath("$").isArray(),
                            jsonPath("$[0].id").value(1),
                            jsonPath("$[0].id").isNumber(),
                            jsonPath("$[0].clienteNome").value("Cliente"),
                            jsonPath("$[0].clienteNome").isString(),
                            jsonPath("$[0].itens").isNotEmpty(),
                            jsonPath("$[0].itens").isArray(),
                            jsonPath("$[0].status").value("PENDENTE_DE_PAGAMENTO"),
                            jsonPath("$[0].status").isString(),
                            jsonPath("$[0].valorTotal").value(1),
                            jsonPath("$[0].valorTotal").isNumber(),
                            jsonPath("$[0].data").isNotEmpty(),
                            jsonPath("$[0].data").isArray()
                    );
        }

        @Test
        @DisplayName("Deve retornar todos os pedidos por prioridade")
        void deveRetornarTodosPedidosPorPrioridade() throws Exception {
            //Arrange
            when(buscaPedidosOrdenadosPorPrioridadeInputPort.buscarPorPrioridade()).thenReturn(PedidoHelper.criaListaPedidoDTO());
            when(pedidoMapperWeb.toPedidoListResponse(anyList())).thenReturn(PedidoHelper.criaListaPedidoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/pedidos/fila-producao"))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$").isNotEmpty(),
                            jsonPath("$").isArray(),
                            jsonPath("$[0].id").value(1),
                            jsonPath("$[0].id").isNumber(),
                            jsonPath("$[0].clienteNome").value("Cliente"),
                            jsonPath("$[0].clienteNome").isString(),
                            jsonPath("$[0].itens").isNotEmpty(),
                            jsonPath("$[0].itens").isArray(),
                            jsonPath("$[0].status").value("PENDENTE_DE_PAGAMENTO"),
                            jsonPath("$[0].status").isString(),
                            jsonPath("$[0].valorTotal").value(1),
                            jsonPath("$[0].valorTotal").isNumber(),
                            jsonPath("$[0].data").isNotEmpty(),
                            jsonPath("$[0].data").isArray()
                    );
        }

        @Test
        @DisplayName("Deve retornar um pedido por id")
        void deveRetornarPedidoPorId() throws Exception {
            //Arrange
            when(buscarPedidoPorIdInputPort.buscarPorId(anyLong())).thenReturn(PedidoHelper.criaPedidoDTO());
            when(pedidoMapperWeb.toPedidoResponse(any(PedidoDTO.class))).thenReturn(PedidoHelper.criaPedidoResponse());

            //Act
            //Assert
            mockMvc.perform(get("/pedidos/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").value(1),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.clienteNome").value("Cliente"),
                            jsonPath("$.clienteNome").isString(),
                            jsonPath("$.itens").isNotEmpty(),
                            jsonPath("$.itens").isArray(),
                            jsonPath("$.status").value("PENDENTE_DE_PAGAMENTO"),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valorTotal").value(1),
                            jsonPath("$.valorTotal").isNumber(),
                            jsonPath("$.data").isNotEmpty(),
                            jsonPath("$.data").isArray()
                    );
        }

        @Test
        @DisplayName("Deve retornar uma cobrança por id do pedido")
        void deveRetornarCobrancaPorIdPedido() throws Exception {
            //Arrange
            when(buscaCobrancaPorPedidoIdInputPort.buscarPorPedidoId(anyLong())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any(CobrancaDTO.class))).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(get("/pedidos/{id}/cobranca", 1))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").value(1),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.pedidoId").value(1),
                            jsonPath("$.pedidoId").isNumber(),
                            jsonPath("$.status").value("PENDENTE"),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valor").value(1),
                            jsonPath("$.valor").isNumber(),
                            jsonPath("$.qrCode").value("1234"),
                            jsonPath("$.qrCode").isString()
                    );
        }

        @Test
        @DisplayName("Deve retornar NotFound quando id informado não existir")
        void deveRetornarNotFound_QuandoIdInformadoNaoExitir() throws Exception {
            //Arrange
            Long id = 1L;
            String mensagem = String.format("Pedido %s não encontrado", id);
            when(buscarPedidoPorIdInputPort.buscarPorId(anyLong())).thenThrow(new EntityNotFoundException(mensagem));

            //Act
            //Assert
            mockMvc.perform(get("/pedidos/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(mensagem));
        }
    }

    @Nested
    @DisplayName("Atualiza status de um pedido")
    class AtualizaStatusPedido {
        @Test
        @DisplayName("Deve atualizar status de um pedido")
        void deveAtualizarStatusPedido() throws Exception {
            //Arrange
            Long id = 1L;
            when(atualizaStatusPedidoInputPort.atualizarStatus(any(), any())).thenReturn(PedidoHelper.criaPedidoDTO());
            when(pedidoMapperWeb.toPedidoResponse(any())).thenReturn(PedidoHelper.criaPedidoResponse());

            //Act
            //Assert
            mockMvc.perform(patch("/pedidos/{id}/status", id)
                            .contentType("application/json")
                            .content(ObjectParaJsonMapper.converte(PedidoHelper.criaAtualizaStatusPedidoRequest())))
                    .andExpect(status().isCreated())
                    .andExpectAll(
                            jsonPath("$.id").value(id),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.clienteNome").value("Cliente"),
                            jsonPath("$.clienteNome").isString(),
                            jsonPath("$.itens").isNotEmpty(),
                            jsonPath("$.itens").isArray(),
                            jsonPath("$.status").value("PENDENTE_DE_PAGAMENTO"),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valorTotal").value(1),
                            jsonPath("$.valorTotal").isNumber(),
                            jsonPath("$.data").isNotEmpty(),
                            jsonPath("$.data").isArray()
                    );
        }

        @Test
        @DisplayName("Deve retornar NotFound ao atualizar status de um pedido")
        void deveRetornarNotFoundAoAtualizarStatusPedido() throws Exception {
            //Arrange
            Long id = 1L;
            String mensagem = String.format("Pedido %s não encontrado", id);
            when(atualizaStatusPedidoInputPort.atualizarStatus(any(), any())).thenThrow(new EntityNotFoundException(mensagem));

            //Act
            //Assert
            mockMvc.perform(patch("/pedidos/{id}/status", 1)
                            .contentType("application/json")
                            .content(ObjectParaJsonMapper.converte(PedidoHelper.criaAtualizaStatusPedidoRequest())))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value(mensagem));
        }

    }
}
