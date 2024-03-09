package br.com.fiap.techchallenge.servicopedido.core.dtos;

public class MensagemDTOBase {
    private final Long idPedido;

    public MensagemDTOBase(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }
}
