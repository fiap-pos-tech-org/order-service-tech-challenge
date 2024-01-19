package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoHelper {

    public static Pedido criaPedido() {
        return new Pedido(1L, StatusPedidoEnum.PENDENTE_DE_PAGAMENTO, ClienteHelper.criaCliente(), LocalDateTime.now(), BigDecimal.valueOf(1L));
    }

    public static PedidoDTO criaPedidoDTO() {
        ClienteDTO clienteDTO = ClienteHelper.criaClienteDTO();
        List<ItemPedidoDTO> itemsPedido = ItemPedidoHelper.criaListaItemPedidoDTO();
        return new PedidoDTO(1L, clienteDTO, itemsPedido, StatusPedidoEnum.PENDENTE_DE_PAGAMENTO, BigDecimal.valueOf(1L), LocalDateTime.now());
    }

    public static PedidoDTO criaPedidoDTOCopia(Pedido pedido) {
        ClienteDTO clienteDTO = new ClienteMapper().toClienteDTO(pedido.getCliente());
        List<ItemPedidoDTO> itemsPedido = new ItemPedidoMapper().toItemPedidoResponse(pedido.getItens());
        return new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                itemsPedido,
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getData()
        );
    }

    public static AtualizaStatusPedidoDTO criaAtualizaStatusPedidoDTO() {
        return new AtualizaStatusPedidoDTO(StatusPedidoEnum.RECEBIDO);
    }

    public static List<Pedido> criaListaPedidos() {
        return List.of(criaPedido());
    }

    public static CriaPedidoDTO criaCriaPedidoDTO() {
        return new CriaPedidoDTO(1L, ItemPedidoHelper.criaListaCriaItemPedidoDTO());
    }

}
