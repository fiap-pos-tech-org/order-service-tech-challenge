package br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProdutoRequest {

    @Schema(example = "HAMBURGER ANGUS")
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
    private String nome;

    @Schema(example = "LANCHE")
    private CategoriaEnum categoria;

    @Schema(example = "35.90")
    private BigDecimal preco;

    @Schema(example = "Hamburger Angus 200mg de carne")
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

    @NotBlank(message = "O campo 'nome' é obrigatório")
    public String getNome() {
        return nome;
    }

    @NotNull(message = "O campo 'categoria' é obrigatório")
    public CategoriaEnum getCategoria() {
        return categoria;
    }

    @NotNull(message = "O campo 'preco' é obrigatório")
    @DecimalMin(value = "0.0", message = "Informe um valor maior que 0.0")
    public BigDecimal getPreco() {
        return preco;
    }

    @NotBlank(message = "O campo 'descricao' é obrigatório")
    public String getDescricao() {return descricao; }

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
