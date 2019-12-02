package com.example.saolonguinho.model;

import com.example.saolonguinho.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Usuario {
    private String cpf;
    private String rg;
    private String cep;
    private String nome;
    private String telefone;
    private String idUsuario;
    private String email;
    private String senha;
    private String dataNascimento;
    private String nomeDaMae;
    private boolean status;
    private String notificacao;

    //DATA EM QUE O USUARIO FEZ O SEU CADASTRO NO FIREBASE
    private String dataCadastroNoBanco;

    //METODO QUE VAI MANDAR OS DADOS PARA O BANCO
    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("Usuarioss").child(this.idUsuario).setValue(this);
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDataCadastroNoBanco() {
        return dataCadastroNoBanco;
    }

    public void setDataCadastroNoBanco(String dataCadastroNoBanco) {
        this.dataCadastroNoBanco = dataCadastroNoBanco;
    }

    public String getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }
}
