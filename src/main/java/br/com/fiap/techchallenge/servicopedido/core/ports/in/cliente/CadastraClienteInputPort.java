package br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;

public interface CadastraClienteInputPort {
    ClienteDTO cadastrar(ClienteDTO cliente);
}
