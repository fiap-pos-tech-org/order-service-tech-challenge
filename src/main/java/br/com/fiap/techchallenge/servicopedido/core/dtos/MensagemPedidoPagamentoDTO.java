package br.com.fiap.techchallenge.servicopedido.core.dtos;

import java.math.BigDecimal;

public final class MensagemPedidoPagamentoDTO extends MensagemDTOBase {
    private final Long idCliente;
    private final BigDecimal valorTotal;

    public MensagemPedidoPagamentoDTO(Long idPedido, Long idCliente, BigDecimal valorTotal) {
        super(idPedido);
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
