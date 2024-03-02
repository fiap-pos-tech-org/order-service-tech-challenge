package br.com.fiap.techchallenge.servicopedido.adapters.web.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnderecoRequest {

    @Schema(example = "Avenida")
    @NotBlank(message = "O campo 'logradouro' é obrigatório")
    private String logradouro;

    @Schema(example = "Brasil")
    @NotBlank(message = "O campo 'rua' é obrigatório")
    private String rua;

    @Schema(example = "1500")
    @NotNull(message = "O campo 'numero' é obrigatório")
    private Integer numero;

    @Schema(example = "Centro")
    @NotBlank(message = "O campo 'bairro' é obrigatório")
    private String bairro;

    @Schema(example = "Uberlândia")
    @NotBlank(message = "O campo 'cidade' é obrigatório")
    private String cidade;

    @Schema(example = "MG")
    @NotBlank(message = "O campo 'estado' é obrigatório")
    private String estado;

    public EnderecoRequest() {
    }

    public EnderecoRequest(String logradouro, String rua, Integer numero, String bairro, String cidade, String estado) {
        this.logradouro = logradouro;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
