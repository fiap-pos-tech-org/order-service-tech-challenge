package br.com.fiap.techchallenge.servicopedido.core.domain.entities;

import br.com.fiap.techchallenge.servicopedido.core.dtos.EnderecoDTO;

public class Endereco {

    private Long id;
    private String logradouro;
    private String rua;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco(Long id, String logradouro, String rua, Integer numero, String bairro, String cidade, String estado) {
        this.id = id;
        this.logradouro = logradouro;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco(EnderecoDTO endereco) {
        this.id = endereco.id();
        this.logradouro = endereco.logradouro();
        this.rua = endereco.rua();
        this.numero = endereco.numero();
        this.bairro = endereco.bairro();
        this.cidade = endereco.cidade();
        this.estado = endereco.estado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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