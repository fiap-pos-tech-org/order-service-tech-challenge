package br.com.fiap.techchallenge.lanchonete.core.usecases.cliente;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cliente;
import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;

public class ClienteTesteBase {

    public static ClienteDTO criaDefaultClienteDTO() {
        return new ClienteDTO("cliente1", "000.000.000-07", "cliente1@email.com");
    }

    public static Cliente criaDefaultCliente() {
        return new Cliente("cliente", "000.000.000-01", "cliente@email.com");
    }

    public static Cliente criaCopiaClienteDTO() {
        ClienteDTO clienteDTO = criaDefaultClienteDTO();
        return new Cliente(
                clienteDTO.nome(),
                clienteDTO.cpf(),
                clienteDTO.email()
        );
    }
}
