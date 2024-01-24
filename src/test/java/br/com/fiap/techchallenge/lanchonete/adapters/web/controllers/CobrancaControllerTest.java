package br.com.fiap.techchallenge.lanchonete.adapters.web.controllers;

import br.com.fiap.techchallenge.lanchonete.adapters.web.CobrancaController;
import br.com.fiap.techchallenge.lanchonete.adapters.web.handlers.ExceptionsHandler;
import br.com.fiap.techchallenge.lanchonete.adapters.web.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.CobrancaRequest;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cobranca.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cobranca.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cobranca.BuscaStatusPagamentoInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cobranca.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.lanchonete.utils.CobrancaHelper;
import br.com.fiap.techchallenge.lanchonete.utils.ObjectParaJsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CobrancaControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CriaCobrancaInputPort criaCobrancaInputPort;
    @Mock
    private BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort;
    @Mock
    private AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;
    @Mock
    private CobrancaMapper cobrancaMapper;
    @Mock
    private BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort;
    private CobrancaController cobrancaController;
    private AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cobrancaController = new CobrancaController(
                criaCobrancaInputPort,
                buscaCobrancaPorIdInputPort,
                atualizaStatusCobrancaInputPort,
                cobrancaMapper,
                buscaStatusPagamentoInputPort
        );

        mockMvc = MockMvcBuilders.standaloneSetup(cobrancaController)
                .setControllerAdvice(new ExceptionsHandler())
                .build();

        atualizaStatusCobrancaRequest = new AtualizaStatusCobrancaRequest(StatusCobrancaEnum.PAGO);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Nested
    @DisplayName("Cadastra uma cobranca")
    class CadastraCobranca {
        @Test
        @DisplayName("Deve cadastrar uma cobrança quando os dados forem informados corretamente")
        void deveCadastrarUmaCobranca_QuandoOsDadosForemInformadosCorretamente() throws Exception {
            //Arrange
            when(criaCobrancaInputPort.criar(any(CriaCobrancaDTO.class))).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(post("/cobrancas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(CobrancaHelper.criaCobrancaRequest())))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status").value("PENDENTE"));

        }

        @Test
        @DisplayName("Deve retornar BadRequest quando o id do pedido não for informado")
        void deveRetornarBadRequest_QuandoPedidoIdNaoForInformado() throws Exception {
            //Arrange
            //Act
            //Assert
            mockMvc.perform(post("/cobrancas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(new CobrancaRequest(null))))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("O campo pedidoId é obrigatório"));

        }
    }

    @Nested
    @DisplayName("Busca uma cobranca")
    class BuscaCobranca {
        @Test
        @DisplayName("Deve retornar uma cobrança quando o id for informado")
        void deveRetornarUmaCobranca_QuandoIdForInformado() throws Exception {
            //Arrange
            when(buscaCobrancaPorIdInputPort.buscarPorId(any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(get("/cobrancas/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("PENDENTE"));
        }

        @Test
        @DisplayName("Deve retornar NotFound quando o id do pedido não for encontrado")
        void deveRetornarNotFound_QuandoIdPedidoNaoEncontrado() throws Exception {
            //Arrange
            when(buscaCobrancaPorIdInputPort.buscarPorId(any())).thenThrow(EntityNotFoundException.class);

            //Act
            //Assert
            mockMvc.perform(get("/cobrancas/{id}", 1))
                    .andExpect(status().isNotFound());

            verify(buscaCobrancaPorIdInputPort, times(1)).buscarPorId(anyLong());
        }
    }

    @Nested
    @DisplayName("Atualiza o status de uma cobranca")
    class AtualizaStatusCobranca {
        @Test
        @DisplayName("Deve atualizar o status de uma cobrança quando o id informado existir e os dados forem informados corretamente")
        void deveAtualizarStatusCobranca_QuandoIdInformadoExistirEDadosCorretos() throws Exception {
            //Arrange
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(post("/cobrancas/{id}/status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(new AtualizaStatusCobrancaRequest(StatusCobrancaEnum.PAGO))))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").isNotEmpty(),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.pedidoId").isNotEmpty(),
                            jsonPath("$.pedidoId").isNumber(),
                            jsonPath("$.status").isNotEmpty(),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valor").isNotEmpty(),
                            jsonPath("$.valor").isNumber(),
                            jsonPath("$.qrCode").isNotEmpty(),
                            jsonPath("$.qrCode").isString()
                    );

            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(anyLong(), any());
            verify(cobrancaMapper, times(1)).toCobrancaResponse(any());
        }

        @Test
        @DisplayName("Deve retornar NotFound quando o id do pedido não for encontrado")
        void deveRetornarNotFound_QuandoIdPedidoNaoEncontrado() throws Exception {
            //Arrange
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenThrow(EntityNotFoundException.class);

            //Act
            //Assert
            mockMvc.perform(post("/cobrancas/{id}/status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(atualizaStatusCobrancaRequest)))
                    .andExpect(status().isNotFound());

            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(anyLong(), any());
        }
    }

    @Nested
    @DisplayName("Atualiza o status de uma cobranca via webhook")
    class AtualizaStatusCobrancaViaWebhook {
        @Test
        @DisplayName("Deve atualizar o status de uma cobrança quando através do webhook do MercadoPago")
        void deveAtualizarStatusCobranca_QuandoIdInformadoExistirEDadosCorretos() throws Exception {
            //Arrange
            when(buscaStatusPagamentoInputPort.buscaStatus(anyLong())).thenReturn(CobrancaHelper.criaStatusPagamentoDTO());
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(post("/cobrancas/{id}/webhook-status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(CobrancaHelper.criaWebhookStatusCobrancaRequest())))
                    .andExpect(status().isOk());

            verify(buscaStatusPagamentoInputPort, times(1)).buscaStatus(anyLong());
            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(anyLong(), any());
        }
    }
}
