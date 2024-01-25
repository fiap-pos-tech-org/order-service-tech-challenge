package br.com.fiap.techchallenge.lanchonete.adapters.web.mappers;

import br.com.fiap.techchallenge.lanchonete.utils.CobrancaHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CobrancaMapperTest {
    @Test
    @DisplayName("Deve retornar um objeto tipo CobrancaResponse quando receber como parâmetro um CobrancaDTO")
    void deveRetornarUmObjetoTipoCobrancaResponse_QuandoReceberComoParametroUmCobrancaDTO() {
        //Arrange
        CobrancaMapper cobrancaMapper = new CobrancaMapper();
        //Act
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(CobrancaHelper.criaCobrancaDTO());
        //Assert
        assertThat(cobrancaResponse).isNotNull();
    }
}
