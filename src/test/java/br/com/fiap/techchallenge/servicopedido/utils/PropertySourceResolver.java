package br.com.fiap.techchallenge.servicopedido.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySourceResolver {

    @Value("${clientes.api.url}")
    private String urlApiclientes;

    public String getUrlApiclientes() {
        return urlApiclientes;
    }
}
