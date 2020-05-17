package com.example.vprojetos.model;

import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.enums.Categoria;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Projeto {


    private String nome; // nome do projeto
    private double dinheiroArrecadado; // Dinherio arrecadado até agora
    private double dinheiroAlvo; // Dinheiro que precisa ser arrecadado
    private String autor; // Autor do projeto
    private long dataInicio; // Data de inicio do projeto
    private long dataFim; // Data de fim do projeto
    private Categoria categoria; // Categoria do projeto
    private String descricaoDoProjeto; // Descricao do Projeto

    private boolean projetoConcluido; // Quando chega na data limite
    private boolean dinheiroArrecadadoComSucesso; // Quando consegue o dinheiro necessário

    private HashMap<String, Double> usuariosDoacoes = new HashMap<String, Double>(); // Usuários que doaram para o projeto
    private HashMap<String, Integer> notas = new HashMap<String, Integer>(); // Notas que os usuários deram para o projeto
    private HashMap<String, String> comentarios = new HashMap<String, String>(); // Comentários dos usuários


    public Projeto(HashMap<String, Object> map){
        // Construtor do banco de dados
        this.nome = (String) map.get("nome");
        this.dinheiroArrecadado = (Double) map.get("dinherioArrecadado");
        this.dinheiroAlvo = (Double) map.get("dinheiroAlvo");
        this.autor = (String) map.get("autor");
        this.dataInicio = (long) map.get("dataInicio");
        this.dataFim = (long) map.get("dataFim");
        this.categoria = Categoria.valueOf( (String) map.get("categoria")  );
        this.descricaoDoProjeto = (String) map.get("descricaoDoProjeto");
        this.projetoConcluido = (boolean) map.get("projetoConcluido");
        this.dinheiroArrecadadoComSucesso = (boolean) map.get("dinheiroArrecadadoComSucesso");

        if(map.containsKey("usuariosDoacoes"))
            this.usuariosDoacoes = (HashMap<String, Double>) map.get("usuariosDoacoes");
        if(map.containsKey("notas"))
            this.notas = (HashMap<String, Integer>) map.get("notas");
        if(map.containsKey("comentarios"))
            this.comentarios = (HashMap<String, String>) map.get("comentarios");

    }


    public Projeto(String nome, Categoria categoria, double dinheiroAlvo, String descricaoDoProjeto) {
        //CONSTRUTOR PROJETO NOVO

        long dataInicio = new Date().getTime();
        long dataFim = calculaDataFim(dataInicio);


        this.nome = nome;
        this.dinheiroAlvo = dinheiroAlvo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.autor = Conexao.getFirebaseAuth().getUid();
        this.categoria = categoria;
        this.descricaoDoProjeto = descricaoDoProjeto;

    }

    private long calculaDataFim(long dataInicio) {
        Date date = new Date(dataInicio);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        long dataFim = c.getTime().getTime();

        return dataFim;
    }

    public String getDescricaoDoProjeto() {
        return descricaoDoProjeto;
    }

    public void setDescricaoDoProjeto(String descricaoDoProjeto) {
        this.descricaoDoProjeto = descricaoDoProjeto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public boolean isProjetoConcluido() {
        return projetoConcluido;
    }

    public void setProjetoConcluido(boolean projetoConcluido) {
        this.projetoConcluido = projetoConcluido;
    }

    public boolean isDinheiroArrecadadoComSucesso() {
        return dinheiroArrecadadoComSucesso;
    }

    public void setDinheiroArrecadadoComSucesso(boolean dinheiroArrecadadoComSucesso) {
        this.dinheiroArrecadadoComSucesso = dinheiroArrecadadoComSucesso;
    }

    public HashMap<String, Integer> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<String, Integer> notas) {
        this.notas = notas;
    }

    public HashMap<String, String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(HashMap<String, String> comentarios) {
        this.comentarios = comentarios;
    }

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

    public HashMap<String, Double> getUsuariosDoacoes() {
        return usuariosDoacoes;
    }

    public void setUsuariosDoacoes(HashMap<String, Double> usuariosDoacoes) {
        this.usuariosDoacoes = usuariosDoacoes;
    }

    public Date dataInicioToDate(){
        Date date = new Date(dataInicio);
        return date;
    }

    public Date dataFimToDate(){
        Date date = new Date(dataFim);
        return date;
    }

    public void recebeDoacao(double novoValor){
        if(projetoConcluido) // Projetos concluidos não podem receber doação
            return;

        String uid = FirebaseAuth.getInstance().getUid();

        if (usuariosDoacoes.containsKey(uid)){
            Double valorDoadoDoUsuario = usuariosDoacoes.get(uid);
            usuariosDoacoes.put(uid, novoValor + valorDoadoDoUsuario);
        }else {
            usuariosDoacoes.put(uid, novoValor);
        }
    }

    public void recebeAvaliacao(int nota){
        String uid = FirebaseAuth.getInstance().getUid();

        notas.put(uid, nota);

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("nome", nome);
        map.put("dinherioArrecadado", dinheiroArrecadado);
        map.put("dinheiroAlvo", dinheiroAlvo);
        map.put("dinheiroArrecadadoComSucesso", dinheiroArrecadadoComSucesso);
        map.put("comentarios", comentarios);
        map.put("dataFim", dataFim);
        map.put("dataInicio", dataInicio);
        map.put("notas", notas);
        map.put("projetoConcluido", projetoConcluido);
        map.put("usuarios", usuariosDoacoes);
        map.put("autor", autor);

        return map;
    }
}
