package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.ClienteRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClienteTesteHelper {

    public static ClienteDTO criaDefaultClienteDTO() {
        return new ClienteDTO("cliente1", "56312729036", "cliente1@email.com");
    }

    public static Cliente criaDefaultCliente() {
        return new Cliente("cliente", "80313100098", "cliente@email.com");
    }

    public static Cliente criaCopiaClienteDTO() {
        ClienteDTO clienteDTO = criaDefaultClienteDTO();
        return new Cliente(
                clienteDTO.nome(),
                clienteDTO.cpf(),
                clienteDTO.email()
        );
    }

    public static ClienteResponse criaDefaultClienteResponse() {
        return new ClienteResponse(1L, "cliente1", "56312729036", "cliente1@email.com");
    }

    public static ClienteRequest criaDefaultClienteRequest() {
        return new ClienteRequest("cliente1", "56312729036", "cliente1@email.com");
    }

    public static String converteParaJson(Object o) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(o);
    }
}
