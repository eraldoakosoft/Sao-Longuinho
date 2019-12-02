package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Cartao extends Modelo {
    //INSTITUIÇÃO EMISSORA DO CARTÃO
    private String bancoEmissor;
    //ULTIMOS 4 DIGITOS DO CARTÃO
    private String digitos;


    public void salvar(){
        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Itens");
        reference.push().setValue(this);
    }

    public Cartao() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("Itens");
        setIdItem(databaseReference.push().getKey());
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

}