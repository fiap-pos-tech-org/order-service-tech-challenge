package br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProdutoRequest {

    @Schema(example = "HAMBURGER ANGUS")
    @Size(max = 30)
    @Pattern(regexp = "^[A-zÀ-ú0-9 -]*$", message = "O campo 'nome' deve corresponder ao padrão ^[A-zÀ-ú0-9 -]*$")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;

    @Schema(example = "LANCHE")
    @NotNull(message = "O campo 'categoria' é obrigatório")
    private CategoriaEnum categoria;

    @Schema(example = "35.90")
    @NotNull(message = "O campo 'preco' é obrigatório")
    @DecimalMin(value = "0.0", message = "Informe um valor maior que 0.0")
    private BigDecimal preco;

    @Schema(example = "Hamburger Angus 200mg de carne")
    @Pattern(regexp = "^[A-zÀ-ú0-9 -]*$", message = "O campo 'descricao' deve corresponder ao padrão ^[A-zÀ-ú0-9 -]*$")
    @NotBlank(message = "O campo 'descricao' é obrigatório")
    private String descricao;

    public ProdutoRequest() {
    }

    public ProdutoRequest(String nome, CategoriaEnum categoria, BigDecimal preco, String descricao) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
    }

    public ProdutoDTO toProdutoDTO() {
        return new ProdutoDTO(
                nome,
                categoria,
                preco,
                descricao
        );
    }

    public String getNome() {
        return nome;
    }

    public CategoriaEnum getCategoria() {
        return categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(CategoriaEnum categoria) {
        this.categoria = categoria;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
