package br.com.fiap.techchallenge.lanchonete;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.enums.StatusCobrancaEnum;

public class AtualizaStatusCobrancaTestBase {

    private AtualizaStatusCobrancaTestBase() {
    }

    public static AtualizaStatusCobrancaRequest criarAtualizaStatusCobrancaRequest(StatusCobrancaEnum statusCobrancaEnum) {
        return new AtualizaStatusCobrancaRequest(statusCobrancaEnum);
    }

}
