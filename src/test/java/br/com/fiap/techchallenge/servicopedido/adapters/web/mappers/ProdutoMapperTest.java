package br.com.fiap.techchallenge.servicopedido.adapters.web.mappers;

import br.com.fiap.techchallenge.servicopedido.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.servicopedido.core.domain.entities.enums.CategoriaEnum;
import br.com.fiap.techchallenge.servicopedido.core.dtos.ProdutoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoMapperTest {
    @Test
    @DisplayName("Deve retornar um objeto ProdutoResponse quando receber como parâmetro um ProdutoDTO")
    void deveRetornarUmObjetoProdutoResponse_QuandoReceberComoParametroUmProdutoDTO() {
        // Arrange
        ProdutoDTO produtoDTO = new ProdutoDTO(
                "X-Bacon",
                CategoriaEnum.LANCHE,
                BigDecimal.valueOf(20.0),
                "Pão, hambúrguer, queijo, bacon, alface, tomate e maionese"
        );
        ProdutoMapper produtoMapper = new ProdutoMapper();

        // Act
        ProdutoResponse produtoResponse = produtoMapper.toProdutoResponse(produtoDTO);

        // Assert
        assertThat(produtoResponse).isNotNull();
    }
}
