package br.com.fiap.techchallenge.servicopedido.core.dtos;

import java.util.List;

public record CriaPedidoDTO(
    Long clientId,
    List<CriaItemPedidoDTO> itens
)  {
}
