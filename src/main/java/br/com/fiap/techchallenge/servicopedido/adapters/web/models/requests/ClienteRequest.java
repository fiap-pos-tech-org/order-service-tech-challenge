package br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests;

import br.com.fiap.techchallenge.servicopedido.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicopedido.core.dtos.EnderecoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ClienteRequest {

    @Schema(example = "Cliente 1")
    @NotBlank(message = "O campo 'nome' é obrigatório")
    private String nome;

    @Schema(example = "94187479015")
    @NotBlank(message = "O campo 'cpf' é obrigatório")
    private String cpf;

    @Schema(example = "cliente1@gmail.com")
    @NotBlank(message = "O campo 'email' é obrigatório")
    private String email;

    @Schema(example = "(34) 99988-7766")
    @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{5}-\\d{4}$", message = "O campo 'telefone' deve corresponder ao padrão ^\\(\\d{2}\\)\\s\\d{5}-\\d{4}$$")
    @NotBlank(message = "O campo 'telefone' é obrigatório")
    private String telefone;

    @Valid
    @NotNull(message = "O campo 'endereco' é obrigatório")
    private EnderecoRequest endereco;

    public ClienteRequest() {
    }

    public ClienteRequest(String nome, String cpf, String email, String telefone, EnderecoRequest endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public ClienteDTO toClienteDTO() {
        var enderecoDTO = new EnderecoDTO(this.endereco.getLogradouro(), this.endereco.getRua(),
                this.endereco.getNumero(), this.endereco.getBairro(), this.endereco.getCidade(),
                this.endereco.getEstado());
        return new ClienteDTO(this.nome, this.cpf, this.email, this.telefone, enderecoDTO);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequest endereco) {
        this.endereco = endereco;
    }
}
