package br.com.fiap.techchallenge.lanchonete.adapters.web.mappers;

import br.com.fiap.techchallenge.lanchonete.utils.ClienteHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteMapperTest {
    @Test
    @DisplayName("Deve retornar um objeto tipo ClienteResponse quando receber como parâmetro um ClienteDTO")
    void deveRetornarUmObjetoTipoClienteResponse_QuandoReceberComoParametroUmClienteDTO() {
        //Arrange
        ClienteMapper clienteMapper = new ClienteMapper();
        //Act
        var clienteResponse = clienteMapper.toClienteResponse(ClienteHelper.criaClienteDTO());
        //Assert
        assertThat(clienteResponse).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar uma lista de objetos tipo ClienteResponse quando receber como parâmetro uma lista de ClienteDTO")
    void deveRetornarUmaListaDeObjetosTipoClienteResponse_QuandoReceberComoParametroUmaListaDeClienteDTO() {
        //Arrange
        ClienteMapper clienteMapper = new ClienteMapper();
        //Act
        var clientesResponse = clienteMapper.toClientesResponse(List.of(ClienteHelper.criaClienteDTO()));
        //Assert
        assertThat(clientesResponse).isNotNull();
    }
}
