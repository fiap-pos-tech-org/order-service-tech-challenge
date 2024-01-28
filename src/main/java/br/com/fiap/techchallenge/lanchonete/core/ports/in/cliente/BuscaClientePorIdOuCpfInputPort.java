package br.com.fiap.techchallenge.lanchonete.core.ports.in.cliente;

import br.com.fiap.techchallenge.lanchonete.core.dtos.ClienteDTO;

public interface BuscaClientePorIdOuCpfInputPort {
    ClienteDTO buscar(String cpf);

    ClienteDTO buscar(Long id);
}
