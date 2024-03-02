package br.com.fiap.techchallenge.servicopedido.utils;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Endereco;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests.EnderecoRequest;
import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.EnderecoResponse;
import br.com.fiap.techchallenge.servicopedido.core.dtos.EnderecoDTO;

public class EnderecoHelper {

    private EnderecoHelper() {
    }

    public static Endereco criaEndereco() {
        return new Endereco(1L, "Avenida", "Brasil", 1500, "Centro",
                "Uberlândia", "MG");
    }

    public static EnderecoDTO criaEnderecoDTO() {
        var endereco = criaEndereco();
        return new EnderecoDTO(endereco.getId(), endereco.getLogradouro(), endereco.getRua(), endereco.getNumero(),
                endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }

    public static EnderecoRequest criaEnderecoRequest() {
        var endereco = criaEndereco();
        return new EnderecoRequest(endereco.getLogradouro(), endereco.getRua(), endereco.getNumero(),
                endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }

    public static EnderecoResponse criaEnderecoResponse() {
        var endereco = criaEndereco();
        return new EnderecoResponse(endereco.getId(), endereco.getLogradouro(), endereco.getRua(), endereco.getNumero(),
                endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
    }

}
