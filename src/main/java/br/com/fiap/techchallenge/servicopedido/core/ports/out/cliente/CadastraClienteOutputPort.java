package br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;

public interface CadastraClienteOutputPort {
    ClienteDTO cadastrar(ClienteDTO cliente);
}
