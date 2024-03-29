package com.example.vprojetos.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.vprojetos.Activity.LoginActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Usuario implements Serializable {
    public static Usuario usuario = new Usuario();

    private String nome;
    private String cpf;
    private String email;
    private HashMap<String, Double> doacoes = new HashMap<String, Double>();
    private HashMap<String, Integer> notas = new HashMap<String, Integer>();
    private HashMap<String, String> comentario = new HashMap<String, String>();
    private List<String> projetosCriados = new ArrayList<>();
    private String bio;
    private HashMap<String, Object> map = new HashMap<String, Object>();
    private String ultimoLogin;
    private String estado;
    private String pais;
    private String dataCriacao;


    public Usuario() {

    }

    public Usuario(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public Usuario(HashMap<String, Object> map) {
        // Construtor do banco de dados
        this.nome = (String) map.get("nome");
        this.cpf = (String) map.get("cpf");
        this.email = (String) map.get("email");
        this.pais = (String) map.get("pais");
        this.estado = (String) map.get("estado");
        this.dataCriacao = (String) map.get("dataCriacao");


        if (map.containsKey("doacoes"))
            this.doacoes = (HashMap<String, Double>) map.get("doacoes");
        if (map.containsKey("notas"))
            this.notas = (HashMap<String, Integer>) map.get("notas");
        if (map.containsKey("comentarios"))
            this.comentario = (HashMap<String, String>) map.get("comentario");


        //Pegando projetos criados, ele pode vir como array
        // list ou hash map, dependendo se um projeto foi deletado ou não
        if (map.containsKey("projetosCriados")) {
            if (map.get("projetosCriados").getClass() == ArrayList.class) {
                this.projetosCriados = (ArrayList) map.get("projetosCriados");
            } else {
                HashMap<String, String> projetosCriadosHashMap = (HashMap<String, String>) map.get("projetosCriados");
                ArrayList<String> projetosCriados = new ArrayList<>();
                for (String nomeProjeto : projetosCriadosHashMap.values()) {
                    projetosCriados.add(nomeProjeto);
                }
                this.projetosCriados = projetosCriados;

            }
        }


        if (map.containsKey("ultimoLogin")) {
            this.ultimoLogin = (String) map.get("ultimoLogin");
        }


    }

    public void setUltimoLogin(String ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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
        if (this.projetosCriados != null) {
            projetosCriados.add(nome);

        }

    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        long ultimoLogin = new Date().getTime();
        String dataFormatada = formatador.format(ultimoLogin);
        this.ultimoLogin = dataFormatada;
    }

    public void addDoacoes(String nome, double novoValor) {

        if (doacoes.containsKey(nome)) {
            Object aDouble = doacoes.get(nome);
            Double valor;
            if (aDouble.getClass() == Long.class) {
                valor = ((Long) aDouble).doubleValue();
            } else {
                valor = (Double) aDouble;
            }

            doacoes.put(nome, novoValor + valor);
        } else {
            doacoes.put(nome, novoValor);
        }

    }

    public void addNotas(String nome, int nota) {
        this.notas.put(nome, nota);
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

}

