package br.com.fiap.techchallenge.servicopedido.core.dtos;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;

import java.util.List;

public class MensagemPedidoProducaoDTO extends MensagemDTOBase {

    private StatusPedidoEnum status;
    private List<ItemPedidoDTO> itens;

    public MensagemPedidoProducaoDTO() {
    }

    public MensagemPedidoProducaoDTO(Long idPedido, StatusPedidoEnum status, List<ItemPedidoDTO> itens) {
        super(idPedido);
        this.status = status;
        this.itens = itens;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }
}
