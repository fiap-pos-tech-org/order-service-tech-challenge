package br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;

public interface AtualizaClienteOutputPort {
    ClienteDTO atualizar(ClienteDTO cliente, Long id);
}
