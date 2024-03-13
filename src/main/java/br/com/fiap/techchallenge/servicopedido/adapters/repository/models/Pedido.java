package br.com.fiap.techchallenge.servicopedido.adapters.repository.models;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.StatusPedidoEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private LocalDateTime data;

    @Nullable
    private Long clienteId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, targetEntity = ItemPedido.class)
    private List<ItemPedido> itens = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        data = LocalDateTime.now();
        status = StatusPedidoEnum.PENDENTE_DE_PAGAMENTO;
        setValorTotal();
    }

    public Pedido() {
    }

    public Pedido(StatusPedidoEnum status, LocalDateTime data, @Nullable Long clienteId, List<ItemPedido> itens) {
        this.status = status;
        this.data = data;
        this.clienteId = clienteId;
        this.itens = itens;
    }

    public Pedido(StatusPedidoEnum status, LocalDateTime data, List<ItemPedido> itens) {
        this.status = status;
        this.data = data;
        this.itens = itens;
    }

    public Pedido(Long id, StatusPedidoEnum status, Long clienteId, LocalDateTime data, BigDecimal valorTotal) {
        this.id = id;
        this.status = status;
        this.clienteId = clienteId;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Pedido(StatusPedidoEnum status, Long clienteId, LocalDateTime data, BigDecimal valorTotal) {
        this.status = status;
        this.clienteId = clienteId;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Pedido(StatusPedidoEnum status, LocalDateTime data, BigDecimal valorTotal) {
        this.status = status;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Nullable
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(@Nullable Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal setValorTotal() {
        return valorTotal;
    }

}
