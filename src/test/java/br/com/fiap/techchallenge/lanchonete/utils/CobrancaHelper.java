package br.com.fiap.techchallenge.lanchonete.utils;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.CobrancaRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.lanchonete.core.dtos.CriaCobrancaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CobrancaHelper {
    public static Cobranca criaCobranca() {
        return new Cobranca(1L, 1L, StatusCobrancaEnum.PENDENTE, BigDecimal.valueOf(1),
                "1234", LocalDateTime.now(), LocalDateTime.now());
    }

    public static CobrancaDTO criaCobrancaDTO() {
        return new CobrancaDTO(1L, 1L, BigDecimal.valueOf(1), StatusCobrancaEnum.PENDENTE, new QrCode("1234"));
    }

    public static Cobranca criaCopiaCobrancaDTO() {
        CobrancaDTO cobrancaDTO = criaCobrancaDTO();
        return new Cobranca(
                cobrancaDTO.id(),
                cobrancaDTO.pedidoId(),
                cobrancaDTO.status(),
                cobrancaDTO.valor(),
                cobrancaDTO.qrCode().getEncodedBase64Value(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static CobrancaResponse criaCobrancaResponse() {
        CobrancaDTO cobrancaDTO = criaCobrancaDTO();
        return new CobrancaResponse(
                1L,
                cobrancaDTO.pedidoId(),
                cobrancaDTO.status(),
                cobrancaDTO.valor(),
                cobrancaDTO.qrCode()
        );
    }

    public static String converteParaJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static CobrancaRequest criaCobrancaRequest() {
        return new CobrancaRequest(1L);
    }
}
