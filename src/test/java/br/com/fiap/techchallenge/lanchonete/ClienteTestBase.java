package br.com.fiap.techchallenge.lanchonete;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.requests.ClienteRequest;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class ClienteTestBase {

    private ClienteTestBase() {
    }

    public static ClienteRequest criarClienteRequest() {
        var clienteRequest = new ClienteRequest();
        clienteRequest.setNome("Cliente Teste");
        clienteRequest.setCpf(gerarCPF());
        clienteRequest.setEmail(gerarEmail());
        return clienteRequest;
    }

    private static String gerarEmail() {
        var random = new Random();
        return String.format("clienteteste%s@gmail.com", random.nextInt(100000));
    }

    private static String gerarCPF() {
        var cpf = new int[11];
        var random = new Random();

        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        cpf[9] = calcularDigitoVerificador(cpf, 10);
        cpf[10] = calcularDigitoVerificador(cpf, 11);

        return Arrays.stream(cpf)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(""));
    }

    private static int calcularDigitoVerificador(int[] cpf, int multiplicador) {
        int soma = 0;
        for (int i = 0; i < multiplicador - 1; i++) {
            soma += cpf[i] * (multiplicador - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

}
