package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Modelo {
    private String idItem;
    private String nome;
    private String tipo;
    private Boolean status;
    private String idLonguinho;
    private String nomeLonguinho;
    private String dataInseridoNoBanco;
    private String dataSaida;
    private String dataEncontrado;
    private String comentario;
    private String ultimaAtualizacao;
    private List<String> fotos;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Modelo");

    public Modelo() {
        this.setIdItem(reference.push().getKey());
    }

    public void salvarModelo(){
        reference.child(getIdItem()).setValue(this);
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdLonguinho() {
        return idLonguinho;
    }

    public void setIdLonguinho(String idLonguinho) {
        this.idLonguinho = idLonguinho;
    }

    public String getNomeLonguinho() {
        return nomeLonguinho;
    }

    public void setNomeLonguinho(String nomeLonguinho) {
        this.nomeLonguinho = nomeLonguinho;
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

    public String getDataEncontrado() {
        return dataEncontrado;
    }

    public void setDataEncontrado(String dataEncontrado) {
        this.dataEncontrado = dataEncontrado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
