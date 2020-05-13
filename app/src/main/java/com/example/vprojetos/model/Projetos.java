package com.example.vprojetos.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.HashMap;
class Projetos {

    private double dinheiroArrecadado;
    private double dinheiroAlvo;

    private long dataInicio;
    private long dataFim;

    private boolean projetoConcluido; // Quando chega na data limite
    private boolean dinheiroArrecadadoComSucesso; // Quando consegue o dinheiro necessário



    private HashMap<String, Double> usuarios = new HashMap<String, Double>();
    private HashMap<String, Integer> notas = new HashMap<String, Integer>();
    private HashMap<String, String> comentarios = new HashMap<String, String>();

    public double getDinheiroArrecadado() {
        return dinheiroArrecadado;
    }

    public void setDinheiroArrecadado(double dinheiroArrecadado) {
        this.dinheiroArrecadado = dinheiroArrecadado;
    }

    public double getDinheiroAlvo() {
        return dinheiroAlvo;
    }

    public void setDinheiroAlvo(double dinheiroAlvo) {
        this.dinheiroAlvo = dinheiroAlvo;
    }

    public long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public long getDataFim() {
        return dataFim;
    }

    public void setDataFim(long dataFim) {
        this.dataFim = dataFim;
    }

    public HashMap<String, Double> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(HashMap<String, Double> usuarios) {
        this.usuarios = usuarios;
    }

    public Date getDataInicioToDate(){
        Date date = new Date(dataInicio);
        return date;
    }

    public Date getDataFimToDate(){
        Date date = new Date(dataFim);
        return date;
    }

    public void recebeDoacao(double novoValor){
        if(projetoConcluido) // Projetos concluidos não podem receber doação
            return;

        String uid = FirebaseAuth.getInstance().getUid();

        if (usuarios.containsKey(uid)){
            Double valorDoadoDoUsuario = usuarios.get(uid);
            usuarios.put(uid, novoValor + valorDoadoDoUsuario);
        }else {
            usuarios.put(uid, novoValor);
        }
    }

    public void recebeAvaliacao(int nota){
        String uid = FirebaseAuth.getInstance().getUid();

        notas.put(uid, nota);

    }
}
