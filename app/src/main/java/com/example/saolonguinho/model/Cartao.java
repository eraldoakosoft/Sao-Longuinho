package com.example.saolonguinho.model;

public class Cartao {
    //NOME IGUAL A ESTÁ NO CARTÃO
    private String nome;
    //INSTITUIÇÃO EMISSORA DO CARTÃO
    private String banco;
    //ULTIMOS 4 DIGITOS DO CARTÃO
    private String digitos;
    //NOME DA PESSOA QUE ACHOU | ID DA PESSOA
    private String nomePessoaachou;
    //DATA EM QUE A PESSOA ACHOU
    private String dataEntrada;
    //DATA EM QUE FOI ENCOTRADO O DONO DO CARTÃO
    private String dataSaida;
    //IDENTIFICADOR UNICO PARA O CARTÃO
    private String idCartao;
    //STATUS DO DADO NO BANCO | SERVE PARA SABER SE O ITEM FOI ENCONTRADO
    private boolean status;

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

    public String getNomePessoaachou() {
        return nomePessoaachou;
    }

    public void setNomePessoaachou(String nomePessoaachou) {
        this.nomePessoaachou = nomePessoaachou;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
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
