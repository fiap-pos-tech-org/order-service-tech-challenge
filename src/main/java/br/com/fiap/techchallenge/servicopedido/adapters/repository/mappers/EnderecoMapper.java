package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.EnderecoResponse;
import br.com.fiap.techchallenge.servicopedido.core.dtos.EnderecoDTO;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public EnderecoDTO toEnderecoDTO(EnderecoResponse endereco) {
        return new EnderecoDTO(endereco.getId(), endereco.getLogradouro(), endereco.getRua(), endereco.getNumero(),
                endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }

}