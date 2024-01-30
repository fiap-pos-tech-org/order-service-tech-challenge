package br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;

public interface AtualizaClienteInputPort {
    ClienteDTO atualizar(ClienteDTO cliente, Long id);
}
