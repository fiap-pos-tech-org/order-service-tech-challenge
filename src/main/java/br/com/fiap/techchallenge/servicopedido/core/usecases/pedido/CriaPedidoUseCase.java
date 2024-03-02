package br.com.fiap.techchallenge.servicopedido.core.usecases.pedido;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.Cliente;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.Endereco;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.ItemPedido;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.Pedido;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.CriaItemPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.CriaPedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.pedido.CriaPedidoInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.pedido.CriaPedidoOutputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.produto.BuscaProdutoPorIdOutputPort;

import java.util.List;

public class CriaPedidoUseCase implements CriaPedidoInputPort {


    private final CriaPedidoOutputPort criaPedidoOutputPort;
    private final BuscaProdutoPorIdOutputPort buscaProdutoPorIdOutputPort;
    private final BuscaClienteOutputPort buscaClienteOutputPort;

    public CriaPedidoUseCase(CriaPedidoOutputPort criaPedidoOutputPort,
                             BuscaProdutoPorIdOutputPort buscaProdutoPorIdOutputPort,
                             BuscaClienteOutputPort buscaClienteOutputPort
    ) {
        this.criaPedidoOutputPort = criaPedidoOutputPort;
        this.buscaProdutoPorIdOutputPort = buscaProdutoPorIdOutputPort;
        this.buscaClienteOutputPort = buscaClienteOutputPort;
    }

    @Override
    public PedidoDTO criar(CriaPedidoDTO pedidoIn) {

        var pedido = new Pedido(StatusPedidoEnum.PENDENTE_DE_PAGAMENTO);

        pedido.setCliente(getCliente(pedidoIn));
        adicionaItemsPedido(pedido, pedidoIn.itens());

        return criaPedidoOutputPort.criar(new PedidoDTO(pedido));
    }

    private Cliente getCliente(CriaPedidoDTO pedidoIn) {
        if (pedidoIn.clientId() != null) {
            var clienteDTO = buscaClienteOutputPort.buscar(pedidoIn.clientId());
            return new Cliente(
                    clienteDTO.id(),
                    clienteDTO.nome(),
                    clienteDTO.cpf(),
                    clienteDTO.email(),
                    clienteDTO.telefone(),
                    new Endereco(clienteDTO.endereco())
            );
        }
        return null;
    }

    private void adicionaItemsPedido(Pedido pedido, List<CriaItemPedidoDTO> listaPedidosItem) {
        listaPedidosItem.forEach(itemPedidoIn -> {
            var produtoOut = buscaProdutoPorIdOutputPort.buscarPorId(itemPedidoIn.produtoId());
            pedido.addItemPedido(
                    new ItemPedido(produtoOut.id(), produtoOut.nome(), produtoOut.descricao(), produtoOut.preco(), itemPedidoIn.quantidade())
            );
        });
    }


}
