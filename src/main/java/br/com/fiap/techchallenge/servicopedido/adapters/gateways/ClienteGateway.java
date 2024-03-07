package br.com.fiap.techchallenge.servicopedido.adapters.gateways;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClienteGateway extends GatewayBase implements BuscaClienteOutputPort {

    private final OkHttpClient httpClient;
    private final ClienteMapper clienteMapper;
    private final String urlApiClientes;

    public ClienteGateway(OkHttpClient httpClient, ClienteMapper clienteMapper, @Value("${clientes.api.url}") String urlApiClientes) {
        this.httpClient = httpClient;
        this.clienteMapper = clienteMapper;
        this.urlApiClientes = urlApiClientes;
    }

    @Override
    public ClienteDTO buscar(Long id) {
        var request = new Request.Builder()
                .url(String.format("%s/%s", urlApiClientes, id))
                .build();
        var clienteResponse = newCall(request, ClienteResponse.class);
        return clienteMapper.toClienteDTO(clienteResponse);
    }

    @Override
    protected OkHttpClient getHttpClient() {
        return this.httpClient;
    }
}
