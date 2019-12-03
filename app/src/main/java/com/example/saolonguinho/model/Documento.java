package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Documento  extends Modelo{
    private String idDocumento;
    private String cpf;
    private String rg;
    private String nomeMae;
    private String dataNascimento;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Documento");

    public void salvar(){
        reference.child(getIdDocumento()).setValue(this);
    }

    public Documento() {
        this.setIdDocumento(reference.push().getKey());
        this.setStatus(true);
        this.setDataSaida("00/00/0000");
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
