package com.example.vprojetos.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public static Usuario usuario = new Usuario();
    private String nome;
    private String cpf;
    private String email;
    private Double dinheiroDoado;
    private String id;
    private List<Projetos> projetosFavoritos = new ArrayList<Projetos>();



    public Usuario() {

    }

    public Usuario(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email=email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
