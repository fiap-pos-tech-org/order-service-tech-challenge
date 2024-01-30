package br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import java.util.List;

public interface BuscaTodosPedidosOutputPort {
    List<PedidoDTO> buscarTodos();
}
