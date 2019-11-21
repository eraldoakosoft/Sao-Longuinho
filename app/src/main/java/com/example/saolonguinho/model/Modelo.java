package com.example.saolonguinho.model;

public class Modelo {
    private String nome;
    private String tipo;
    private Boolean status;
    private String idLonguinho;
    private String nomeLonguinho;
    private String dataInseridoNoBanco;
    private String dataSaida;
    private String dataEncontrado;
    private String descricao;
    private String ultimaAtualizacao;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Modelo() {
        this.status = true;
        this.dataSaida = "00/00/0000";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdLonguinho() {
        return idLonguinho;
    }

    public void setIdLonguinho(String idLonguinho) {
        this.idLonguinho = idLonguinho;
    }

    public String getNomeLonguinho() {
        return nomeLonguinho;
    }

    public void setNomeLonguinho(String nomeLonguinho) {
        this.nomeLonguinho = nomeLonguinho;
    }

    public String getDataInseridoNoBanco() {
        return dataInseridoNoBanco;
    }

    public void setDataInseridoNoBanco(String dataInseridoNoBanco) {
        this.dataInseridoNoBanco = dataInseridoNoBanco;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getDataEncontrado() {
        return dataEncontrado;
    }

    public void setDataEncontrado(String dataEncontrado) {
        this.dataEncontrado = dataEncontrado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
