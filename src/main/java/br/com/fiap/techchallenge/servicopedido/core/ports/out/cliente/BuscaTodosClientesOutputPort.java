package br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import java.util.List;

public interface BuscaTodosClientesOutputPort {
    List<ClienteDTO> buscarTodos();
}
