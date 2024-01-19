//package br.com.fiap.techchallenge.lanchonete.core.usecases.cobranca;
//
//import br.com.fiap.techchallenge.lanchonete.adapters.gateways.pagamentos.mercadopago.StatusPagamento;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.squareup.okhttp.Headers;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//import com.squareup.okhttp.internal.http.RealResponseBody;
//import okio.BufferedSource;
//import okio.Okio;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class BuscaStatusPagamentoUseCase_ {
//
//    @Mock
//    private OkHttpClient httpClient;
//    @InjectMocks
//    private StatusPagamento statusPagamento;
//    private BuscaStatusPagamentoUseCase pagamentoUseCase;
//    private Headers headers;
//    private BufferedSource source;
//    private Response response;
//    AutoCloseable openMocks;
//
//    @BeforeEach
//    void setup() throws JsonProcessingException {
//        openMocks = MockitoAnnotations.openMocks(this);
//
//        pagamentoUseCase = new BuscaStatusPagamentoUseCase(statusPagamento);
//        headers = new Headers.Builder()
//                .add("header1", "value1")
//                .build();
//
//        source = Okio.buffer(Okio.source(
//
//        ));
//
//        response = new Response.Builder()
//                .code(200)
//                .body(new RealResponseBody(headers, source))
//                .build();
//    }
//
//    @Test
//    void deveRetornarStatusCorretoDoPagamento_QuantoInformadoUmIdDePagamentoValido() throws IOException {
//        //Arrange
//        when(httpClient.newCall(any(Request.class)).execute()).thenReturn(response);
//
//        //Act
//
//        //Assert
//
//    }
//}
