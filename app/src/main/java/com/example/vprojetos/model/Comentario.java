package com.example.vprojetos.model;

import java.io.Serializable;

public class Comentario implements Serializable {

    private String nomeUsuario;
    private String descricao;
    private Long dataComentario;

    public Comentario(String nomeUsuario, String descricao, Long dataComentario) {
        this.nomeUsuario = nomeUsuario;
        this.descricao = descricao;
        this.dataComentario = dataComentario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getDataComentario() {
        return dataComentario;
    }
}
