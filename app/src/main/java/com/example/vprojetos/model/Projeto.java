package com.example.vprojetos.model;

import android.util.Log;

import com.example.vprojetos.config.Conexao;
import com.example.vprojetos.enums.Categoria;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Projeto implements Serializable {


    private String nome; // nome do projeto
    private String nomeAutor; // nome do Autor do projeto
    private double dinheiroArrecadado; // Dinherio arrecadado até agora
    private double dinheiroAlvo; // Dinheiro que precisa ser arrecadado
    private String UidAutor; // Autor do projeto
    private long dataInicio; // Data de inicio do projeto
    private long dataFim; // Data de fim do projeto
    private Categoria categoria; // Categoria do projeto
    private String descricaoDoProjeto; // Descricao do Projeto

    private boolean projetoConcluido; // Quando chega na data limite
    private boolean dinheiroArrecadadoComSucesso; // Quando consegue o dinheiro necessário

    private HashMap<String, Double> usuariosDoacoes = new HashMap<String, Double>(); // Usuários que doaram para o projeto
    private HashMap<String, Integer> notas = new HashMap<String, Integer>(); // Notas que os usuários deram para o projeto
    private HashMap<String, String> comentarios = new HashMap<String, String>(); // Comentários dos usuários
    private HashMap<String, Comentario> comentariosHash = new HashMap<>(); // Comentários do usuário


    public Projeto(HashMap<String, Object> map) {
        // Construtor do banco de dados

        this.nome = (String) map.get("nome");
        this.UidAutor = (String) map.get("uidAutor");
        this.nomeAutor = (String) map.get("nomeAutor");

        this.dinheiroArrecadado = Double.parseDouble(map.get("dinheiroArrecadado").toString());
        this.dinheiroAlvo = Double.parseDouble(map.get("dinheiroAlvo").toString());

        this.dataInicio = Long.parseLong(map.get("dataInicio").toString());
        this.dataFim = Long.parseLong(map.get("dataFim").toString());

        this.categoria = Categoria.valueOf((String) map.get("categoria"));
        this.descricaoDoProjeto = (String) map.get("descricaoDoProjeto");
        this.projetoConcluido = Boolean.parseBoolean(map.get("projetoConcluido").toString());

        this.dinheiroArrecadadoComSucesso = Boolean.parseBoolean(map.get("dinheiroArrecadadoComSucesso").toString());


        if (map.containsKey("usuariosDoacoes")){
            this.usuariosDoacoes = (HashMap<String, Double>) map.get("usuariosDoacoes");
            Log.i("teste", "achou as doações");

        }
        if (map.containsKey("notas")) {
            HashMap<String, Long> notas = (HashMap<String, Long>) map.get("notas");
            HashMap<String, Integer> notasInteger = new HashMap<>();

            for (String key : notas.keySet()) {
                Long aLong = notas.get(key);
                notasInteger.put(key, aLong.intValue());
            }
            this.notas = notasInteger;
        }
        if (map.containsKey("comentarios"))
            this.comentarios = (HashMap<String, String>) map.get("comentarios");

        if (map.containsKey("comentariosHash")) {
            HashMap<String, HashMap<String, Object>> comentariosHash =
                    (HashMap<String, HashMap<String, Object>>) map.get("comentariosHash");

            for (String key : comentariosHash.keySet()) {

                HashMap<String, Object> stringObjectHashMap = comentariosHash.get(key);

                Long dataComentario = (Long) stringObjectHashMap.get("dataComentario");
                String descricao = (String) stringObjectHashMap.get("descricao");
                String nomeUsuario = (String) stringObjectHashMap.get("nomeUsuario");


                Log.i("comentariosHash", dataComentario.getClass() + "");
                Log.i("comentariosHash", descricao.getClass() + "");
                Log.i("comentariosHash", nomeUsuario.getClass() + "");

                Comentario comentario = new Comentario(nomeUsuario, descricao, dataComentario);
                if(this.comentariosHash != null)
                    this.comentariosHash.put(key, comentario);

            }


        }


    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Projeto(String nome, Categoria categoria, double dinheiroAlvo, String descricaoDoProjeto) {
        //CONSTRUTOR PROJETO NOVO

        long dataInicio = new Date().getTime();
        long dataFim = calculaDataFim(dataInicio);


        this.nome = nome;
        this.dinheiroAlvo = dinheiroAlvo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.UidAutor = Conexao.getFirebaseAuth().getUid();
        this.nomeAutor = Usuario.usuario.getNome();
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

    public void adicionarNota(String uid, int nota) {
        this.notas.put(uid, nota);
    }

    public String getUidAutor() {
        return UidAutor;
    }

    public void setUidAutor(String uidAutor) {
        this.UidAutor = uidAutor;
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

    public void setComentariosHash(HashMap<String, Comentario> comentariosHash) {
        this.comentariosHash = comentariosHash;
    }

    public void addComentario(Comentario comentario) {
        this.comentariosHash.put(comentario.getDataComentario() + "", comentario);
    }


    public HashMap<String, Comentario> getComentariosHash() {
        return comentariosHash;
    }

    public Date dataInicioToDate() {
        Date date = new Date(dataInicio);
        return date;
    }

    public Date dataFimToDate() {
        Date date = new Date(dataFim);
        return date;
    }

    public void addDoacao(double novoValor) {
        String uid = FirebaseAuth.getInstance().getUid();

        if (usuariosDoacoes.containsKey(uid)) {

            try{
            Double valor;
            Object aDouble = usuariosDoacoes.get(uid);
            if(aDouble.getClass() == Long.class){
                valor = ((Long) aDouble).doubleValue();
            }else{
                valor = (Double) aDouble;
            }

            usuariosDoacoes.put(uid, novoValor + valor);

            }catch (Exception e){
                Log.d("teste.", e.getMessage());
            }



        } else {
            usuariosDoacoes.put(uid, novoValor);
        }

        dinheiroArrecadado += novoValor;

    }

    public void recebeAvaliacao(int nota) {
        String uid = FirebaseAuth.getInstance().getUid();

        notas.put(uid, nota);

    }

    public float mediaNotas() {

        int soma = 0;
        for(String key: notas.keySet()){
            soma += notas.get(key);
        }

        float media = 0;
        if(notas.size() > 0){
            media = soma / notas.size();
        }


        return media;
    }


}
