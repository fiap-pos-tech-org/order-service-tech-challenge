package br.com.fiap.techchallenge.servicopedido.core.dtos;

import br.com.fiap.techchallenge.servicopedido.core.domain.entities.Cliente;

public record ClienteDTO(Long id, String nome, String cpf, String email, String telefone, EnderecoDTO endereco) {

    public ClienteDTO(String nome, String cpf, String email, String telefone, EnderecoDTO endereco) {
        this(null, nome, cpf, email, telefone, endereco);
    }

    public ClienteDTO(Long id) {
        this(id, null, null, null, null, null);
    }

    public ClienteDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getTelefone(),
                new EnderecoDTO(cliente.getEndereco()));
    }

}
