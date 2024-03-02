package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Endereco;
import br.com.fiap.techchallenge.servicopedido.core.dtos.EnderecoDTO;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco toEndereco(EnderecoDTO endereco) {
        return new Endereco(endereco.id(), endereco.logradouro(), endereco.rua(), endereco.numero(),
                endereco.bairro(), endereco.cidade(), endereco.estado());
    }

    public EnderecoDTO toEnderecoDTO(Endereco endereco) {
        return new EnderecoDTO(endereco.getId(), endereco.getLogradouro(), endereco.getRua(), endereco.getNumero(),
                endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }
}
