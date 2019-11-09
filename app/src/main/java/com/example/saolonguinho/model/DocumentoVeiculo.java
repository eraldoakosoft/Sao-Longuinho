package com.example.saolonguinho.model;

public class DocumentoVeiculo {
    private String nome;
    private String cpf;
    private String placa;
    private String modelo;
    private String idPessoaAchou;
    private String dataEncontrado;
    private String dataSaida;
    private String dataEntradaNoBanco;
    private boolean status;

    public DocumentoVeiculo() {
        this.status = true;
        this.dataSaida = "00-00-0000";
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getIdPessoaAchou() {
        return idPessoaAchou;
    }

    public void setIdPessoaAchou(String idPessoaAchou) {
        this.idPessoaAchou = idPessoaAchou;
    }

    public String getDataEncontrado() {
        return dataEncontrado;
    }

    public void setDataEncontrado(String dataEncontrado) {
        this.dataEncontrado = dataEncontrado;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getDataEntradaNoBanco() {
        return dataEntradaNoBanco;
    }

    public void setDataEntradaNoBanco(String dataEntradaNoBanco) {
        this.dataEntradaNoBanco = dataEntradaNoBanco;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
