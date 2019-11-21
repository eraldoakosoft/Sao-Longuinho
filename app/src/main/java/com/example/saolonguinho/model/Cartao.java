package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Cartao extends Modelo {
    //INSTITUIÇÃO EMISSORA DO CARTÃO
    private String bancoEmissor;
    //ULTIMOS 4 DIGITOS DO CARTÃO
    private String digitos;
    //IDENTIFICADOR UNICO PARA O CARTÃO
    private String idCartao;


    private List<String> fotos;

    public Cartao() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("Modelo");
        setIdCartao(databaseReference.push().getKey());
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

    public String getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(String idCartao) {
        this.idCartao = idCartao;
    }
}