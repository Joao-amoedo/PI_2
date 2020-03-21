package com.example.vprojetos.model;

public class Usuario {

    private String nomeCompleto;
    private String CPF;
    private String email;
    private String senha;
    private String confirmarSenha;

    public Usuario() {

    }

    public String getNome() {
        return nomeCompleto;
    }

    public void setNome(String nome) {
        this.nomeCompleto = nome;
    }

    public String getSobrenome() {
        return CPF;
    }

    public void setSobrenome(String sobrenome) {
        this.CPF = sobrenome;
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

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
}
