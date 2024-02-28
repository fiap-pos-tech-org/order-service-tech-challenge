package br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoRequest {

    @Schema(example = "1")
    private Long produtoId;

    @Schema(example = "2")
    private Integer quantidade;

    public ItemPedidoRequest() {
    }

    public ItemPedidoRequest(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    @NotNull(message = "O campo 'produtoId' é obrigatório")
    public Long getProdutoId() {
        return produtoId;
    }

    @NotNull(message = "O campo 'quantidade' é obrigatório")
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
