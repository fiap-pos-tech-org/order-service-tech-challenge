package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    private final EnderecoMapper enderecoMapper;

    public ClienteMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public ClienteDTO toClienteDTO(ClienteResponse clienteResponse) {
        return new ClienteDTO(
                clienteResponse.getId(),
                clienteResponse.getNome(),
                clienteResponse.getCpf(),
                clienteResponse.getEmail(),
                clienteResponse.getTelefone(),
                enderecoMapper.toEnderecoDTO(clienteResponse.getEndereco())
        );
    }
}
