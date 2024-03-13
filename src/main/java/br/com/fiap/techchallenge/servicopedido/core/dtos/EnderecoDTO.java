package br.com.fiap.techchallenge.servicopedido.core.dtos;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.Endereco;

public record EnderecoDTO(Long id, String logradouro, String rua, Integer numero, String bairro, String cidade,
                          String estado) {

    public EnderecoDTO(Endereco endereco) {
        this(endereco.getId(), endereco.getLogradouro(), endereco.getRua(), endereco.getNumero(),
                endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }

}