package com.example.saolonguinho.model;

public class Cartao {
    //NOME IGUAL A ESTÁ NO CARTÃO
    private String nome;
    //INSTITUIÇÃO EMISSORA DO CARTÃO
    private String banco;
    //ULTIMOS 4 DIGITOS DO CARTÃO
    private String digitos;
    //NOME DA PESSOA QUE ACHOU | ID DA PESSOA
    private String idPessoaachou;
    //DATA EM QUE A FOI INSERIDO NO BANCO
    private String dataInseridoNoBanco;
    //DATA EM QUE FOI ENCOTRADO O DONO DO CARTÃO
    private String dataSaida;
    //IDENTIFICADOR UNICO PARA O CARTÃO
    private String idCartao;
    //STATUS DO DADO NO BANCO | SERVE PARA SABER SE O ITEM FOI ENCONTRADO
    private boolean status;
    //DATA EM QUE A PESSOA ACHOU
    private String dataEncontrado;

    public String getDataEncontrado() {
        return dataEncontrado;
    }

    public void setDataEncontrado(String dataEncontrado) {
        this.dataEncontrado = dataEncontrado;
    }

    public Cartao() {
        this.dataSaida = "00-00-0000";
        this.status = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public String getIdPessoaachou() {
        return idPessoaachou;
    }

    public void setIdPessoaachou(String nomePessoaachou) {
        this.idPessoaachou = nomePessoaachou;
    }

    public String getDataEntrada() {
        return dataInseridoNoBanco;
    }

    public void setDataEntradaNoBanco(String dataEntrada) {
        this.dataInseridoNoBanco = dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(String idCartao) {
        this.idCartao = idCartao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
