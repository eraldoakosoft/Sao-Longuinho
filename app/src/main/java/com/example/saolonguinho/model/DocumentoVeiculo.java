package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class DocumentoVeiculo extends Modelo{
    private String idDocVei;
    private String cpf;
    private String placa;
    private String modelo;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("DocumentoVeiculo");


    public void salvar(){
        reference.child(getIdDocVei()).setValue(this);
    }

    public DocumentoVeiculo() {
       this.setIdDocVei(reference.push().getKey());
       this.setStatus(true);
       this.setDataSaida("00/00/0000");
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
