package br.com.fiap.techchallenge.servicopedido.core.usecases.cliente;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.core.ports.in.cliente.BuscaClientePorIdOuCpfInputPort;
import br.com.fiap.techchallenge.servicopedido.core.ports.out.cliente.BuscaClienteOutputPort;

public class BuscaClientePorIdOuCpfIdOuCpfUseCase implements BuscaClientePorIdOuCpfInputPort {

    private final BuscaClienteOutputPort buscaClienteOutputPort;

    public BuscaClientePorIdOuCpfIdOuCpfUseCase(BuscaClienteOutputPort buscaClienteOutputPort) {
        this.buscaClienteOutputPort = buscaClienteOutputPort;
    }

    @Override
    public ClienteDTO buscar(String cpf) {
        return buscaClienteOutputPort.buscar(cpf);
    }

    @Override
    public ClienteDTO buscar(Long id) {
        return buscaClienteOutputPort.buscar(id);
    }
}
