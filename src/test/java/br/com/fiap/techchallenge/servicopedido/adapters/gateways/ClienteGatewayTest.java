package br.com.fiap.techchallenge.servicopedido.adapters.gateways;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicopedido.utils.ClienteHelper;
import br.com.fiap.techchallenge.servicopedido.utils.PropertySourceResolver;
import br.com.fiap.techchallenge.servicopedido.utils.ResponseHelper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClienteGatewayTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;
    @Autowired
    private JsonMapper jsonMapper;
    @Mock
    private OkHttpClient okHttpClient;
    @Mock
    private Call call;
    @Mock
    private ClienteMapper clienteMapper;
    @InjectMocks
    private ClienteGateway clienteGateway;

    private AutoCloseable openMocks;

    private ResponseHelper responseHelper;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        clienteGateway = new ClienteGateway(okHttpClient, clienteMapper, propertySourceResolver.getUrlApiclientes(), jsonMapper);
        responseHelper = new ResponseHelper(jsonMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar o cliente por id")
    void deveBuscarCliente_QuandoInformadoUmId() throws Exception {
        var clienteDTO = ClienteHelper.criaClienteDTO();
        var clienteResponse = ClienteHelper.criaClienteResponse();
        var response = responseHelper.getResponse(clienteResponse, 200);

        //Arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(clienteMapper.toClienteDTO(any(ClienteResponse.class))).thenReturn(clienteDTO);

        //Act
        var resultado = clienteGateway.buscar(1L);

        //Assert
        assertThat(resultado).isNotNull().satisfies(req -> {
            assertThat(req.id()).isEqualTo(1L);
            assertThat(req.nome()).isEqualTo("cliente1");
            assertThat(req.cpf()).isNotNull();
            assertThat(req.email()).isNotNull();
            assertThat(req.telefone()).isEqualTo("(34) 99988-7766");
            assertThat(req.endereco()).satisfies(enderecoDTO -> {
                assertThat(enderecoDTO.id()).isEqualTo(1L);
                assertThat(enderecoDTO.logradouro()).isEqualTo("Avenida");
                assertThat(enderecoDTO.rua()).isEqualTo("Brasil");
                assertThat(enderecoDTO.numero()).isEqualTo(1500);
                assertThat(enderecoDTO.bairro()).isEqualTo("Centro");
                assertThat(enderecoDTO.cidade()).isEqualTo("Uberlândia");
                assertThat(enderecoDTO.estado()).isEqualTo("MG");
            });
        });

        verify(okHttpClient).newCall(any(Request.class));
    }

}

