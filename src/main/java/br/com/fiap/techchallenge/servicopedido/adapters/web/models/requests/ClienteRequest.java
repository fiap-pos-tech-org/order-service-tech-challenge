package br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import io.swagger.v3.oas.annotations.media.Schema;

public class ClienteRequest {

    @Schema(example = "Cliente 1")
    private String nome;

    @Schema(example = "94187479015")
    private String cpf;

    @Schema(example = "cliente1@gmail.com")
    private String email;

    public ClienteRequest() {
    }

    public ClienteRequest(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public ClienteDTO toClienteDTO() {
        return new ClienteDTO(this.nome, this.cpf, this.email);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
