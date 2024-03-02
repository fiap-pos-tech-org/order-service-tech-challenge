package br.com.fiap.techchallenge.servicopedido.adapters.web.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ClienteMapperWeb")
public class ClienteMapper {

    private final EnderecoMapper enderecoMapper;

    public ClienteMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public ClienteResponse toClienteResponse(ClienteDTO cliente) {
        return new ClienteResponse(
                cliente.id(),
                cliente.nome(),
                cliente.cpf(),
                cliente.email(),
                cliente.telefone(),
                enderecoMapper.toEnderecoResponse(cliente.endereco())
        );
    }

    public List<ClienteResponse> toClientesResponse(List<ClienteDTO> clientes) {
        return clientes.stream().map(this::toClienteResponse).toList();
    }
}
