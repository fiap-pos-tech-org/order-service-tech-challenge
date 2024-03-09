package br.com.fiap.techchallenge.servicopedido.core.dtos;

import java.util.List;

public final class MensagemPedidoProducaoDTO extends MensagemDTOBase {
    private final List<ItemPedidoDTO> itens;

    public MensagemPedidoProducaoDTO(Long idPedido, List<ItemPedidoDTO> itens) {
        super(idPedido);
        this.itens = itens;
    }

    public List<ItemPedidoDTO> itens() {
        return itens;
    }
}
