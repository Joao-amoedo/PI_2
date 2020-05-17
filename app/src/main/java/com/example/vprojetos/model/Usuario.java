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
    private List<String> projetosCriados = new ArrayList<>();

    private HashMap<String, Object> map = new HashMap<String, Object>();



    public Usuario(){

    }

    public Usuario(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public Usuario(HashMap<String, Object> map){
        // Construtor do banco de dados
        this.nome = (String) map.get("nome");
        this.cpf = (String) map.get("cpf");
        this.email = (String) map.get("email");

        if (map.containsKey("doacoes"))
            this.doacoes = (HashMap<String, Double>) map.get("doacoes");
        if(map.containsKey("notas"))
            this.notas = (HashMap<String, Integer>) map.get("notas");
        if(map.containsKey("comentarios"))
            this.comentario = (HashMap<String, String>) map.get("comentario");
        if(map.containsKey("projetosCriados"))
            this.projetosCriados = (List<String>) map.get("projetosCriados");



    }

    public List<String> getProjetosCriados() {
        return projetosCriados;
    }

    public void setProjetosCriados(List<String> projetosCriados) {
        this.projetosCriados = projetosCriados;
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

    public void addProjetoCriado(String nome) {
        if(this.projetosCriados != null){
            projetosCriados.add(nome);

        }
    }
}
