package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Documento  extends Modelo{
    private String cpf;
    private String rg;
    private String nomeMae;
    private String dataNascimento;

    public void salvar(){
        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Itens");
        reference.push().setValue(this);
    }

    public Documento() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("Itens");
        setIdItem(databaseReference.push().getKey());
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
