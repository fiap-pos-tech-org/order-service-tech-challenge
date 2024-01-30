package br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;

public interface BuscaClientePorIdOuCpfInputPort {
    ClienteDTO buscar(String cpf);

    ClienteDTO buscar(Long id);
}
