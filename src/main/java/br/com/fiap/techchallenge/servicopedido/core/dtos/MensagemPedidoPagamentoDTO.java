package br.com.fiap.techchallenge.servicopedido.core.dtos;

import java.math.BigDecimal;

public class MensagemPedidoPagamentoDTO extends MensagemDTOBase {
    private Long idCliente;
    private BigDecimal valorTotal;

    public MensagemPedidoPagamentoDTO() {
    }

    public MensagemPedidoPagamentoDTO(Long idPedido, Long idCliente, BigDecimal valorTotal) {
        super(idPedido);
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
