package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.BuscaTodosPedidosInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.BuscaTodosPedidosOutputPort;

import java.util.List;

public class BuscaTodosPedidosUseCase implements BuscaTodosPedidosInputPort {

    private final BuscaTodosPedidosOutputPort buscaTodosPedidosOutputPort;
    private final BuscaClienteOutputPort buscaClienteOutputPort;

    public BuscaTodosPedidosUseCase(BuscaTodosPedidosOutputPort buscaTodosPedidosOutputPort,
                                    BuscaClienteOutputPort buscaClienteOutputPort) {
        this.buscaTodosPedidosOutputPort = buscaTodosPedidosOutputPort;
        this.buscaClienteOutputPort = buscaClienteOutputPort;
    }

    @Override
    public List<PedidoDTO> buscarTodos() {
        var pedidos = buscaTodosPedidosOutputPort.buscarTodos();

        return pedidos.stream()
                .map(pedidoDTO -> {
                    var clienteDTO = getCliente(pedidoDTO.cliente());
                    return pedidoDTO.comCliente(clienteDTO);
                }).toList();
    }

    private ClienteDTO getCliente(ClienteDTO clienteDTO) {
        return clienteDTO != null ? buscaClienteOutputPort.buscar(clienteDTO.id()) : null;
    }

}
