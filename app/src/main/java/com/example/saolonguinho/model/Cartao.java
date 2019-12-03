package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Cartao extends Modelo {
    //INSTITUIÇÃO EMISSORA DO CARTÃO
    private String bancoEmissor;
    //ULTIMOS 4 DIGITOS DO CARTÃO
    private String digitos;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Cartao");


    public void salvar(){
        reference.child(getIdItem()).setValue(this);
    }

    public Cartao() {
        this.setIdItem(reference.push().getKey());
        this.setStatus(true);
        this.setDataSaida("00/00/0000");
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