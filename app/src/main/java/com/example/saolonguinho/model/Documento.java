package com.example.saolonguinho.model;

public class Documento {
    private String nome;
    private String cpf;
    private String rg;
    private String naturalidade;
    private String nomeMae;
    private String idQuemAchou;
    private String dataNascimento;
    private String dataCadastroNoBanco;
    private String dataAchado;
    private String dataSaida;
    private boolean status;

    public Documento() {
        this.dataSaida = "00-00-0000";
        this.status = true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getIdQuemAchou() {
        return idQuemAchou;
    }

    public void setIdQuemAchou(String idQuemAchou) {
        this.idQuemAchou = idQuemAchou;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataCadastroNoBanco() {
        return dataCadastroNoBanco;
    }

    public void setDataCadastroNoBanco(String dataCadastroNoBanco) {
        this.dataCadastroNoBanco = dataCadastroNoBanco;
    }

    public String getDataAchado() {
        return dataAchado;
    }

    public void setDataAchado(String dataAchado) {
        this.dataAchado = dataAchado;
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


}
