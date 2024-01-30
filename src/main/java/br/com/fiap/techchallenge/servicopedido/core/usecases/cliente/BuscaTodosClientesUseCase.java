package br.com.fiap.techchallenge.servicopedido.core.usecases.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente.BuscaTodosClientesInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaTodosClientesOutputPort;

import java.util.List;

public class BuscaTodosClientesUseCase implements BuscaTodosClientesInputPort {

    private final BuscaTodosClientesOutputPort buscaTodosClientesOutputPort;

    public BuscaTodosClientesUseCase(BuscaTodosClientesOutputPort buscaTodosClientesOutputPort) {
        this.buscaTodosClientesOutputPort = buscaTodosClientesOutputPort;
    }

    @Override
    public List<ClienteDTO> buscarTodos() {
        return buscaTodosClientesOutputPort.buscarTodos();
    }
}
