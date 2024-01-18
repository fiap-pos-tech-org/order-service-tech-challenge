package br.com.fiap.techchallenge.lanchonete;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.WebhookDataRequest;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.WebhookStatusCobrancaRequest;

public class WebhookStatusCobrancaTestBase {

    private WebhookStatusCobrancaTestBase() {
    }

    public static WebhookStatusCobrancaRequest criarWebhookStatusCobrancaRequest(String action, Long id) {
        return new WebhookStatusCobrancaRequest(action, criarWebhookDataRequest(id));
    }

    private static WebhookDataRequest criarWebhookDataRequest(Long id) {
        return new WebhookDataRequest(id);
    }

}
