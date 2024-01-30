package br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import java.util.List;

public interface BuscaTodosClientesInputPort {
    List<ClienteDTO> buscarTodos();
}
