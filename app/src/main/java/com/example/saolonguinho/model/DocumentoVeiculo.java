package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class DocumentoVeiculo extends Modelo{
    private String idDocVei;
    private String cpf;
    private String placa;
    private String modelo;

    public DocumentoVeiculo() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("Modelo");
        setIdDocVei( databaseReference.push().getKey() );
    }


    public String getIdDocVei() {
        return idDocVei;
    }

    public void setIdDocVei(String idDocVei) {
        this.idDocVei = idDocVei;
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
}
