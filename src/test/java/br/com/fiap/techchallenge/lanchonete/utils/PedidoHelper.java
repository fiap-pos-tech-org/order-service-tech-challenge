package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.ItemPedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.AtualizaStatusPedidoRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.PedidoRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.PedidoResponse;
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

    public static List<PedidoDTO> criaListaPedidoDTO() {
        return List.of(criaPedidoDTO());
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

    public static PedidoRequest criaPedidoRequest() {
        return new PedidoRequest(
                1L,
                ItemPedidoHelper.criaListaItemPedidoRequest()
        );
    }

    public static PedidoResponse criaPedidoResponse() {
        return new PedidoResponse(
                1L,
                "Cliente",
                ItemPedidoHelper.criaListaItemPedidoResponse(),
                StatusPedidoEnum.PENDENTE_DE_PAGAMENTO,
                BigDecimal.valueOf(1L),
                LocalDateTime.now()
        );
    }

    public static List<PedidoResponse> criaListaPedidoResponse() {
        PedidoResponse pedidoResponse = criaPedidoResponse();
        return List.of(pedidoResponse);
    }

    public static AtualizaStatusPedidoRequest criaAtualizaStatusPedidoRequest() {
        return new AtualizaStatusPedidoRequest(StatusPedidoEnum.RECEBIDO);
    }
}
