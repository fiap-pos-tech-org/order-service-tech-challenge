package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CobrancaDTO;

import java.math.BigDecimal;

public class CobrancaTesteHelper {
    public static Cobranca criaDefaultCobranca() {
        return new Cobranca(1L, StatusCobrancaEnum.PAGO, BigDecimal.valueOf(1), "1234");
    }

    public static CobrancaDTO criaDefaultCobrancaDTO() {
        return new CobrancaDTO(1L, 1L, BigDecimal.valueOf(1), StatusCobrancaEnum.PAGO, new QrCode("1234"));
    }

    public static Cobranca criaCopiaCobrancaDTO() {
        CobrancaDTO cobrancaDTO = criaDefaultCobrancaDTO();
        return new Cobranca(
                cobrancaDTO.pedidoId(),
                cobrancaDTO.status(),
                cobrancaDTO.valor(),
                cobrancaDTO.qrCode().getEncodedBase64Value()
        );
    }
}
