package br.com.fiap.techchallenge.lanchonete.adapters.web.mappers;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.lanchonete.utils.ItemPedidoHelper;
import br.com.fiap.techchallenge.lanchonete.utils.PedidoHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PedidoMapperTest {
    @Test
    @DisplayName("Deve retornar um objeto tipo PedidoResponse quando receber como parâmetro um PedidoDTO")
    void deveRetornarUmObjetoTipoPedidoResponse_QuandoReceberComoParametroUmPedidoDTO() {
        //Arrange
        PedidoMapper pedidoMapper = new PedidoMapper();
        //Act
        var pedidoResponse = pedidoMapper.toPedidoResponse(PedidoHelper.criaPedidoDTO());
        //Assert
        assertThat(pedidoResponse).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar uma lista de objetos tipo PedidoResponse quando receber como parâmetro uma lista de PedidoDTO")
    void deveRetornarUmaListaDeObjetosTipoPedidoResponse_QuandoReceberComoParametroUmaListaDePedidoDTO() {
        //Arrange
        PedidoMapper pedidoMapper = new PedidoMapper();
        //Act
        var pedidoResponse = pedidoMapper.toPedidoListResponse(PedidoHelper.criaListaPedidoDTO());
        //Assert
        assertThat(pedidoResponse).isNotNull();
    }
}
