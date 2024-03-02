package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

    private final EnderecoMapper enderecoMapper;

    public ClienteMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Cliente toCliente(ClienteDTO cliente) {
        return new Cliente(
                cliente.nome(),
                cliente.cpf(),
                cliente.email(),
                cliente.telefone(),
                enderecoMapper.toEndereco(cliente.endereco())
        );
    }

    public ClienteDTO toClienteDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                enderecoMapper.toEnderecoDTO(cliente.getEndereco())
        );
    }

    public List<ClienteDTO> toClienteListDTO(List<Cliente> clientes) {
        return clientes.stream().map(this::toClienteDTO).toList();
    }
}
