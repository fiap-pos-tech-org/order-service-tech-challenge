package br.com.fiap.techchallenge.servicopedido.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.repository.jpa.ClienteJpaRepository;
import br.com.fiap.techchallenge.servicopedido.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.servicopedido.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    private final ItemPedidoMapper itemPedidoMapper;
    private final EnderecoMapper enderecoMapper;
    private final ClienteMapper clienteMapper;
    private final ClienteJpaRepository clienteJpaRepository;

    public PedidoMapper(ItemPedidoMapper itemPedidoMapper, EnderecoMapper enderecoMapper, ClienteMapper clienteMapper,
                        ClienteJpaRepository clienteJpaRepository) {
        this.itemPedidoMapper = itemPedidoMapper;
        this.enderecoMapper = enderecoMapper;
        this.clienteMapper = clienteMapper;
        this.clienteJpaRepository = clienteJpaRepository;
    }

    public Pedido toPedido(PedidoDTO pedidoIn) {
        var cliente = pedidoIn.cliente() != null
                ? clienteJpaRepository.findById(pedidoIn.cliente().id())
                .orElseThrow(() -> new EntityNotFoundException("Cliente " + pedidoIn.id() + " não encontrado"))
                : null;

        var pedido = cliente != null
                ? new Pedido(pedidoIn.status(), cliente, pedidoIn.dataCriacao(), pedidoIn.valorTotal())
                : new Pedido(pedidoIn.status(), pedidoIn.dataCriacao(), pedidoIn.valorTotal());

        var itemPedido = itemPedidoMapper.toItemPedido(pedido, pedidoIn.itens());
        pedido.setItens(itemPedido);
        return pedido;

    }

    public PedidoDTO toPedidoDTO(Pedido pedido) {
        var cliente = pedido.getCliente();
        var clienteDTO = cliente != null ? clienteMapper.toClienteDTO(cliente) : null;
        var listaItemPedidoOut = itemPedidoMapper.toItemPedidoResponse(pedido.getItens());

        return new PedidoDTO(
                pedido.getId(), clienteDTO, listaItemPedidoOut, pedido.getStatus(), pedido.getValorTotal(), pedido.getData());

    }
}
