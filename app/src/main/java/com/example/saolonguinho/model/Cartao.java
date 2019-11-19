package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Cartao {
    //NOME IGUAL A ESTÁ NO CARTÃO
    private String nome;
    //INSTITUIÇÃO EMISSORA DO CARTÃO
    private String bancoEmissor;
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
    //TIPO DE CARÃO
    private String tipo;
    //UMA BREVE DESCRIÇÃO DO INTEM
    private String descricao;

    private List<String> fotos;

    public Cartao() {
        this.dataSaida = "00-00-0000";
        this.status = true;
        DatabaseReference  databaseReference = ConfiguracaoFirebase.getFirebase().child("Cartoes1");
        setIdCartao( databaseReference.push().getKey() );
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(String idCartao) {
        this.idCartao = idCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBancoEmissor() {
        return bancoEmissor;
    }

    public void setBancoEmissor(String bancoEmissor) {
        this.bancoEmissor = bancoEmissor;
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

    public void setIdPessoaachou(String idPessoaachou) {
        this.idPessoaachou = idPessoaachou;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDataEncontrado() {
        return dataEncontrado;
    }

    public void setDataEncontrado(String dataEncontrado) {
        this.dataEncontrado = dataEncontrado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
