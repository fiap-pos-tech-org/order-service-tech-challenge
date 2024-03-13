package br.com.fiap.techchallenge.servicopedido.adapters.web.controllers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.PedidoController;
import br.com.fiap.techchallenge.servicopedido.adapters.web.handlers.ExceptionsHandler;
import br.com.fiap.techchallenge.servicopedido.adapters.web.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.BuscaTodosPedidosInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.CriaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.utils.PedidoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PedidoControllerTest {
    private MockMvc mockMvc;
    private AutoCloseable openMocks;
    @Autowired
    private ObjectMapper mapper;
    @Mock
    private CriaPedidoInputPort criaPedidoInputPort;
    @Mock
    private BuscaTodosPedidosInputPort buscaTodosPedidosInputPort;
    @Mock
    private BuscarPedidoPorIdInputPort buscarPedidoPorIdInputPort;
    @Mock
    private PedidoMapper pedidoMapperWeb;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        var pedidoController = new PedidoController(
                criaPedidoInputPort,
                buscaTodosPedidosInputPort,
                buscarPedidoPorIdInputPort,
                pedidoMapperWeb
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
                            .content(mapper.writeValueAsString(PedidoHelper.criaPedidoRequest())))
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

}
