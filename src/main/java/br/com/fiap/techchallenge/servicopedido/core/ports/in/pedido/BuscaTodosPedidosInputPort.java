package br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido;


import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import java.util.List;

public interface BuscaTodosPedidosInputPort {
    List<PedidoDTO> buscarTodos();
}
