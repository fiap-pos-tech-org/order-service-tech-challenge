package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;

public class BuscarPedidoPorIdUseCase implements BuscarPedidoPorIdInputPort {
    private final BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;
    private final BuscaClienteOutputPort buscaClienteOutputPort;

    public BuscarPedidoPorIdUseCase(BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort,
                                    BuscaClienteOutputPort buscaClienteOutputPort) {
        this.buscarPedidoPorIdOutputPort = buscarPedidoPorIdOutputPort;
        this.buscaClienteOutputPort = buscaClienteOutputPort;
    }

    @Override
    public PedidoDTO buscarPorId(Long id) {
        var pedidoDTO = buscarPedidoPorIdOutputPort.buscarPorId(id);
        var clienteDTO = getCliente(pedidoDTO.cliente());
        return pedidoDTO.comCliente(clienteDTO);
    }

    private ClienteDTO getCliente(ClienteDTO clienteDTO) {
        return clienteDTO != null ? buscaClienteOutputPort.buscar(clienteDTO.id()) : null;
    }
}
