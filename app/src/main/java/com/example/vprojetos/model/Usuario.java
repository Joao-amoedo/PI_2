package com.example.vprojetos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {
    public static Usuario usuario = new Usuario();

    private String nome;
    private String cpf;
    private String email;
    private HashMap<String, Double> doacoes = new HashMap<String, Double>();
    private HashMap<String, Integer> notas = new HashMap<String, Integer>();
    private HashMap<String, String> comentario = new HashMap<String, String>();


    private HashMap<String, Object> map = new HashMap<String, Object>();


    public Usuario() {

    }

    public Usuario(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public Usuario(String nome, String cpf,
                   String email,
                   HashMap<String, Double> doacoes,
                   HashMap<String, Integer> notas,
                   HashMap<String, String> comentario) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.doacoes = doacoes;
        this.notas = notas;
        this.comentario = comentario;
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

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Usuario.usuario = usuario;
    }

    public HashMap<String, Double> getDoacoes() {
        return doacoes;
    }

    public void setDoacoes(HashMap<String, Double> doacoes) {
        this.doacoes = doacoes;
    }

    public HashMap<String, Integer> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<String, Integer> notas) {
        this.notas = notas;
    }

    public HashMap<String, String> getComentario() {
        return comentario;
    }

    public void setComentario(HashMap<String, String> comentario) {
        this.comentario = comentario;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

    public HashMap<String, Object> toMap() {
        map.put("nome", nome);
        map.put("cpf", cpf);
        map.put("email", email);
        map.put("doacoes", doacoes);
        map.put("notas", notas);
        map.put("comentarios", comentario);
        return map;

    }
}
